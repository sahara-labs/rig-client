 
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
