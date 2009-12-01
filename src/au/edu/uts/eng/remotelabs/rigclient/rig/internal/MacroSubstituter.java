/**
 * SAHARA Rig Client
 * 
 * Software abstraction of physical rig to provide rig session control
 * and rig device control. Automatically tests rig hardware and reports
 * the rig status to ensure rig goodness.
 *
 * @license See LICENSE in the top level directory for complete license terms.
 *
 * Copyright (c) 2009, University of Technology, Sydney
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
 * @date 5th November 2009
 *
 * Changelog:
 * - 05/11/2009 - mdiponio - Initial file creation.
 */
package au.edu.uts.eng.remotelabs.rigclient.rig.internal;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Substitutes macro variables into a string.  All variables must be prefixed
 * with '<code>--</code>' and end in '<code>--<code>'. The following is the 
 * list of macro variable name and macro value pairs.
 * <ul>
 *      <li><code>__FILE__</code> - Filename provided as a construction 
 *      parameter.</li>
 *      <li><code>__USER__</code> - User name provided as a construction
 *      parameter.</li>
 *      <li><code>__DAY_OF_MONTH__</code> - Day of the month in two digit 
 *      numerical format.</li>
 *      <li><code>__MONTH__</code> - Month in two digit numerical format.</li>
 *      <li><code>__YEAR__</code> - ISO-8601 year number.</li>
 *      <li><code>__HOUR__</code> - 24-hour format of an hour with leading 
 *      zeros.</li>
 *      <li><code>__MINUTE__</code> - Minutes with leading zeros.</li>
 *      <li><code>__SECOND__</code> - Seconds with leading zeros.</li>
 *      <li><code>__ISO8601__</code> - ISO 8601 formatted date.</li>
 *      <li><code>__EPOCH__</code> - Seconds since the Unix Epoch.</li>
 * </ul>
 * <strong>NOTE:</strong>For any date/time macros, the value is the time
 * specified by a <code>Calendar</code> instance provided as a construction
 * parameter or if none provided, the current date/time.
 */
public class MacroSubstituter
{
    /**
     * Substitution macros.
     */
    enum Subs 
    {
        /** File substitution. */
        FILE, /** User name substitution. */
        USER, /** Day of month substitution. */
        DAY, /** Month in year. */
        MONTH, /** Year number. */
        YEAR, /** Minute in hour. */
        HOUR, /** Hour in day 24 hour format. */
        MINUTE, /** Second in minute. */
        SECOND, /** ISO8601 time. */
        ISO, /** Epoch time. */
        EPOCH 
    }
    
    /** File name to substitute for <code>__FILE__</code>. */
    private final String fileName;
    
    /** User name to substitute for <code>__USER__</code>. */
    private final String userName;
    
    /** Date/Time to provide for date/time related substitutions. */
    private final Calendar calendar;
    
    /** List of macros. */
    private final Map<String, Subs> macros;
    
    /**
     * Constructor.
     * 
     * @param macroBuilder contains mandatory and optional parameters for construction
     */
    private MacroSubstituter(final MacroBuilder macroBuilder)
    {
        this.fileName = macroBuilder.fileName;
        this.userName = macroBuilder.userName;
        this.calendar = macroBuilder.calendar;
        
        this.macros = new HashMap<String, Subs>();
        this.macros.put("FILE", Subs.FILE);
        this.macros.put("USER", Subs.USER);
        this.macros.put("DAY_OF_MONTH", Subs.DAY);
        this.macros.put("MONTH", Subs.MONTH);
        this.macros.put("YEAR", Subs.YEAR);
        this.macros.put("HOUR", Subs.HOUR);
        this.macros.put("MINUTE", Subs.MINUTE);
        this.macros.put("SECOND", Subs.SECOND);
        this.macros.put("ISO8601", Subs.ISO);
        this.macros.put("EPOCH", Subs.EPOCH);
    }
    
    /**
     * Returns the provided string with any macro variables substituted with 
     * their macro values. If <code>null</code> is passed as a parameter,
     * <code>null</code> is returned.
     *   
     * @param str string to replace macros with values
     * @return value substituted string
     */
    public String substituteMacros(final String str)
    {
        if (str == null) return null;
        
        final String elements[] = str.split("__");
        final StringBuffer buf = new StringBuffer(str.length());
        
        for (String e : elements)
        {
            if (this.macros.containsKey(e))
            {
                switch (this.macros.get(e))
                {
                    case FILE:
                        buf.append(this.fileName);
                        break;
                    case USER:
                        buf.append(this.userName);
                        break;
                    case DAY:
                        buf.append(this.addLeadingZeros(this.calendar.get(Calendar.DAY_OF_MONTH)));
                        break;
                    case MONTH:
                        buf.append(this.addLeadingZeros(this.calendar.get(Calendar.MONTH) + 1));
                        break;
                    case YEAR:
                        buf.append(this.addLeadingZeros(this.calendar.get(Calendar.YEAR)));
                        break;
                    case HOUR:
                        buf.append(this.addLeadingZeros(this.calendar.get(Calendar.HOUR_OF_DAY)));
                        break;
                    case MINUTE:
                        buf.append(this.addLeadingZeros(this.calendar.get(Calendar.MINUTE)));
                        break;
                    case SECOND:
                        buf.append(this.addLeadingZeros(this.calendar.get(Calendar.SECOND)));
                        break;
                    case ISO:
                        /* Format of ISO 8061 date and time can be found at 
                         * http://www.iso.org/iso/date_and_time_format#what-iso-8601-covers. */
                        buf.append(this.calendar.get(Calendar.YEAR));
                        buf.append('-');
                        buf.append(this.addLeadingZeros(this.calendar.get(Calendar.MONTH) + 1));
                        buf.append('-');
                        buf.append(this.addLeadingZeros(this.calendar.get(Calendar.DAY_OF_MONTH)));
                        buf.append('T');
                        buf.append(this.addLeadingZeros(this.calendar.get(Calendar.HOUR_OF_DAY)));
                        buf.append(':');
                        buf.append(this.addLeadingZeros(this.calendar.get(Calendar.MINUTE)));
                        buf.append(':');
                        buf.append(this.addLeadingZeros(this.calendar.get(Calendar.SECOND)));
                        break;
                    case EPOCH:
                        buf.append(this.calendar.getTimeInMillis() / 1000);
                        break;
                }
            }
            else
            {
                buf.append(e);
            }
        }
        
        return buf.toString();
    }

    /**
     * Returns a string containing a two digit representation of the provided
     * string. If the <code>val</code> contains a single digit, a '0' 
     * character is prepended to the string.
     * 
     * @param val value to add leading zero if less then two digits long
     * @return two digit representation of the provided value 
     */
    protected String addLeadingZeros(final int val)
    {
        String str = String.valueOf(val);
        if (str.length() == 1)
        {
            str = "0" + str;
        }
            
        return str;
    }
    
    /**
     * Builder pattern inner class to build {@link MacroSubstituter} instances.
     */
    public static class MacroBuilder
    {
        /** File name to substitute for <code>__FILE__</code>. */
        private final String fileName;
        
        /** User name to substitute for <code>__USER__</code>. */
        private final String userName;
        
        /** Date/Time to provide for date/time related substitutions. */
        private Calendar calendar = Calendar.getInstance();
        
        /**
         * Constructs the builder with the mandatory parameters.
         * 
         * @param file file name to substitute for <code>__FILE__</code>
         * @param user user name to substitute for <code>__USER__</code>
         */
        public MacroBuilder(final String file, final String user)
        {
            this.fileName = file;
            this.userName = user;
        }
        
        /**
         * @param calendar the calendar to set
         */
        public MacroBuilder setCalendar(final Calendar calendar)
        {
            this.calendar = calendar;
            return this;
        }

        /**
         * Build a {@link MacroSubstituter} instance.
         * 
         * @return instance of {@link MacroSubstituter}
         * @throws IllegalStateException if the <code>file</code> or 
         *         <code>user</code> are provided as null.
         */
        public MacroSubstituter build()
        {
            if (this.fileName == null || this.userName == null)
            {
                throw new IllegalStateException();
            }
            return new MacroSubstituter(this);
        }
    }
}
