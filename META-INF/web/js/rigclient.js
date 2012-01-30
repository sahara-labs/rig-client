 
/**
 * SAHARA Web Interface
 *
 * User interface to Sahara Remote Laboratory system.
 *
 * @license See LICENSE in the top level directory for complete license terms.
 *
 * Copyright (c) 2010, University of Technology, Sydney
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  * Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *  * Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 *  * Neither the name of the University of Technology, Sydney nor the names
 *    of its contributors may be used to endorse or promote products derived from
 *    this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * @author Michael Diponio (mdiponio)
 * @date 20th September 2010
 */

function initPage()
{
	resizeFooter();
	menuSlider.init("menu", "slide");
}

var menuSlider = function() {
	var m, e, g, s, q, i;
	e = [];
	q = 8;
	i = 8;
	return {
		init : function(j, k) {
			m = document.getElementById(j);
			e = m.getElementsByTagName('li');
			var i, l, w, p;
			i = 0;
			l = e.length;
			for (i; i < l; i++) {
				var c, v;
				c = e[i];
				v = c.value;
				if (v == 1) {
					s = c;
					w = c.offsetWidth;
					p = c.offsetLeft
				}
				c.onmouseover = function() {
					menuSlider.mo(this)
				};
				c.onmouseout = function() {
					menuSlider.mo(s)
				};
			}
			g = document.getElementById(k);
			g.style.width = w + 'px';
			g.style.left = p + 'px';
		},
		mo : function(d) {
			clearInterval(m.tm);
			var el, ew;
			el = parseInt(d.offsetLeft);
			ew = parseInt(d.offsetWidth);
			m.tm = setInterval(function() {
				menuSlider.mv(el, ew);
			}, i);
		},
		mv : function(el, ew) {
			var l, w;
			l = parseInt(g.offsetLeft);
			w = parseInt(g.offsetWidth);
			if (l != el || w != ew) {
				if (l != el) {
					var ld, lr, li;
					ld = (l > el) ? -1 : 1;
					lr = Math.abs(el - l);
					li = (lr < q) ? ld * lr : ld * q;
					g.style.left = (l + li) + 'px';
				}
				if (w != ew) {
					var wd, wr, wi;
					wd = (w > ew) ? -1 : 1;
					wr = Math.abs(ew - w);
					wi = (wr < q) ? wd * wr : wd * q;
					g.style.width = (w + wi) + 'px';
				}
			} else {
				clearInterval(m.tm);
			}
		}
	};
}();

function resizeFooter()
{
	 var height;
	 
	 if (typeof window.innerWidth != 'undefined')
	 {
	      height = window.innerHeight;
	 }
	 else
     {
		 height = document.documentElement.clientHeight;
     }

	var n = document.getElementById("wrapper");
	n.style.minHeight = height + "px";
}

function formFocusIn() 
{
	$(this).css("border", "1px solid #606060");
}

function formFocusOut()
{
	$(this).css("border", "1px solid #AAAAAA");
}


function loadToolTip(pre, id, tt)
{
	if (tt[id])
	{
		$(pre + "hov" + id).fadeIn();
		$(pre + id).css('font-weight', 'bold');
	}
}

function updateLogs()
{
	$.get(
		"/logs/update",
		null,
		function (response) {
			response = $.trim(response);
			if (response.indexOf("<li") == 0)
			{
				$("#logslist").empty().append(response);
				
				if (!$("#fatalcheck").is(":checked")) $(".fatallog").hide();
				if (!$("#pricheck").is(":checked")) $(".prilog").hide();
				if (!$("#errorcheck").is(":checked")) $(".errorlog").hide();
				if (!$("#warncheck").is(":checked")) $(".warnlog").hide();
				if (!$("#infocheck").is(":checked"))  $(".infolog").hide();
				if (!$("#debugcheck").is(":checked")) $(".debuglog").hide();
			}
			else if (response != "") window.location.reload();
			
			setTimeout(updateLogs, 5000);
		}
	);
}

function updateStatus()
{
	$.get(
		"/status/update",
		null,
		function (response) {
			response = $.trim(response);
			if (response.indexOf("<div") == 0)
			{
				$("#statuscontents").empty().append(response);
			}
			else if (response != "") 
			{
				window.location.reload();
			}
			
			setTimeout(updateStatus, 10000);
		}
	);
}

function updateNavStatus()
{
	$.get(
		"/status/current",
		null,
		function (response) {
			response = $.trim(response);
			if (response.indexOf("<img") == 0)
			{
				$("#currentstatusicon").empty().append(response);
				setTimeout(updateNavStatus, 5000);
			}
		}
	);
}

function restartRigClient()
{
	$("#confirmationcontainer").empty().addClass('confirminprogress').append(
		"<div><img src='/img/spinner.gif' alt='Loading' /></div>\n" +
		"Restarting rig client..."
	);
	
	$.post(
		"/op/restart",
		null,
		function() {
			setTimeout(checkIfRestarted, 5000);
		}
	);
}

function expungeCache()
{
	$("#confirmationcontainer").empty().addClass('confirminprogress').append(
		"<div><img src='/img/spinner.gif' alt='Loading' /></div>\n" +
		"Expunging direct control cache..."
	);
	
	$.post(
		"/op/cache",
		null,
		function() {
			setTimeout(function() {
				window.location.replace("/");
			}, 2000);
		}
	);
}


function checkIfRestarted()
{
	$.get(
		"/status/current",
		null,
		function (response) {
			if (response != '') {
				window.location.replace("/");
			}
		}
	);
}

function shutdownRigClient()
{
	$("#confirmationcontainer").empty().addClass('confirminprogress').append(
		"<div><img src='/img/spinner.gif' alt='Loading' /></div>\n" +
		"Shutting down rig client..."
	);
	
	$.post(
		"/op/shutdown",
		null,
		function() {
			$("#confirmationcontainer").empty().append(
				"Rig client has shutdown."
			);
		}
	);
}

function clearMaintenance()
{
	$("#confirmationcontainer").empty().addClass('confirminprogress').append(
		"<div><img src='/img/spinner.gif' alt='Loading' /></div>\n" +
		"Clearing maintenance..."
	);
		
	$.post(
		"/op/clear",
		null,
		function() {
			window.location.replace("/");
	});
}

function cleanBackups()
{
	$("#confirmationcontainer").empty().addClass('confirminprogress').append(
			"<div><img src='/img/spinner.gif' alt='Loading' /></div>\n" +
			"Cleaning backups..."
	);

	$.post(
		"/op/clean",
		null,
		function() {
			window.location.replace("/");
	});
}

function updateSelectedInfoTab()
{
	$.get(
		"/info/" + selectedTab,
		null,
		function (response) {
			response = $.trim(response);
			if (response.indexOf("<tr") == 0)
			{
				$("#contentstable").empty()
								  .append(response);
				
				$("#contentstable tr:even").addClass("evenrow");
				$("#contentstable tr:odd").addClass("oddrow");
			}
			else if (response != "") window.location.reload();
			
			setTimeout(updateSelectedInfoTab, 5000);
		}
	);
}

function loadInfoTab(tab)
{
	/* Change selected tab. */
	selectedTab = tab;
	
	$('.selectedtab').removeClass('selectedtab')
					 .addClass('notselectedtab');
	$("#" + tab + "tab").removeClass('notselectedtab')
	                    .addClass('selectedtab');
	
	$.get(
		"/info/" + tab,
		null,
		function (response) {
			response = $.trim(response);
			if (response.indexOf("<tr") == 0)
			{
				$("#contentstable").empty()
								  .append(response);
				
				$("#contentstable tr:even").addClass("evenrow");
				$("#contentstable tr:odd").addClass("oddrow");
			}
			else window.location.reload();
		}
	);
}

function loadConfStanza(stanza, callback)
{
	$.get(
		"/config/" + stanza,
		null,
		function(data) {
			/* Content pane. */
	        $("#contentspane")
	        	.empty()
	        	.append(data);
	        $("#confform").validationEngine();
	        
	        $("#confform input")
	        	.focusin(formFocusIn)
	        	.focusout(formFocusOut);
	        
	        if (callback) callback.call();
		}
	);
	
	$(".selectedtab").removeClass("selectedtab")
					 .addClass("notselectedtab");
	$("#" + stanza.replace(/\s/g, "") + "tab").removeClass('notselectedtab')
											.addClass('selectedtab');
}

function resizeConfPanel()
{
	var height = $(window).height() - 230;
    var tabsHeight = $('#lefttablist').height();
    
    if (height < tabsHeight) height = tabsHeight;
    $('#contentspane').css('height', height);
}

var propNum = 1;
function addConfProp()
{
	/* New configuration properties generally get added to 'Other', so the displayed
	 * stanza is switched to 'Other' so there is no interaction where a new 
	 * configuration property is entered in another stanza and when saved it no
	 * longer is displayed there. */
	
	if ($("#lefttablist .selectedtab").attr("id") == 'Othertab')
	{
		addProp();
	}
	else
	{
		loadConfStanza('Other', addProp);
	}
}

function addProp()
{
	var pid = 'NEW_PROP_KEY_' + propNum;
	var vid = 'NEW_PROP_VAL_' + propNum;
	
	var html = "<tr class='";
	if ($("#contentstable tr").last().hasClass("evenrow")) html += "oddrow";
	else html += "evenrow";		
	html += "'>" +
				"<td class='pcol'>" +
					"<div style='width:305px;'>" +
						"<input id='" + pid + "' name='" + pid + "' class='NEW_PROP_KEY' value='' text='' />" +
					"</div>" + 
				"</td>" +
				"<td class='pval'>" +
					"<div style='width:305px;'>" +
						"<input id='" + vid + "' name='" + vid + "' value='' text='' />" +
					"</div>" +
				"</td>" +
			"</tr>"; 
	
	$("#contentstable").append(html);
	
	$("#NEW_PROP_KEY_" + propNum + ", #NEW_PROP_VAL_" + propNum).focusin(formFocusIn).focusout(formFocusOut);
	propNum++;
}

function saveConf() 
{
	var props = {},
		stanza = $("#lefttablist .selectedtab").attr('id');
	
	stanza = stanza.substring(0, stanza.indexOf('tab'))
	
	$("#confform .pval input").each(function() {
		var name = $(this).attr('name');
		if (name.indexOf("NEW_PROP_VAL_") === 0)
		{
			var key = $("#NEW_PROP_KEY_" + name.substring(13)).val();
			if (key.length > 0)
			{
				props[key] = $(this).val();
			}
		}
		else props[name] = $(this).val();
	});
	
	$.post(
			"/config/" + stanza,
			props,
			function(resp) {
				/* Content pane. */
		        $("#contentspane")
		        	.empty()
		        	.append(resp);
		        $("#confform").validationEngine();
		        
		        $("#confform input")
		        	.focusin(formFocusIn)
		        	.focusout(formFocusOut);
		        
		        if (stanza == 'Other')
		        {
		        	loadConfStanza('Other');
		        }
			}
	);
}

function loadAboutTab(tab)
{
	/* Change tab indicator. */
	$("#lefttabbar .selectedtab").removeClass("selectedtab").addClass("notselectedtab");
	$("#" + tab + "tab").removeClass("notselectedtab").addClass("selectedtab");
	
	/* Change tab contents. */
	$("#contentspane .displayed").removeClass("displayed").addClass("notdisplayed");	
	$("#" + tab + "contents").removeClass("notdisplayed").addClass("displayed");
}
