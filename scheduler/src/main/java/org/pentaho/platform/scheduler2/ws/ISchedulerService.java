/*!
 *
 * This program is free software; you can redistribute it and/or modify it under the
 * terms of the GNU Lesser General Public License, version 2.1 as published by the Free Software
 * Foundation.
 *
 * You should have received a copy of the GNU Lesser General Public License along with this
 * program; if not, you can obtain a copy at http://www.gnu.org/licenses/old-licenses/lgpl-2.1.html
 * or from the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 *
 * Copyright (c) 2002-2023 Hitachi Vantara. All rights reserved.
 *
 */

package org.pentaho.platform.scheduler2.ws;

import java.util.Map;
import javax.jws.WebService;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.pentaho.platform.api.scheduler2.ComplexJobTrigger;
import org.pentaho.platform.api.scheduler2.Job;
import org.pentaho.platform.api.scheduler2.SchedulerException;
import org.pentaho.platform.api.scheduler2.SimpleJobTrigger;

/**
 * A service interface used for exposing scheduling capabilities as JAXWS or other endpoints.
 * <p>
 * <strong>WARNING: If you change this interface or any of the objects related to this API, you are effectively changing
 * the WSDL which is generated by JAXWS from this interface. Changing the WSDL means you are changing the contract by
 * which clients talk to the scheduling service.</strong>
 * <p>
 * Currently {@link SimpleJobTrigger} and {@link ComplexJobTrigger} are natively JAXB marshallable. Each trigger class
 * contains JAXB annotations to allow us to use the real trigger object model and not to use a XmlJavaTypeAdapter class.
 * For the {@link Job} object, we are using the {@link JobAdapter} to send the {@link Job} over the wire. We realize
 * that two different approaches were taken here and perhaps a unified approach is best, but each object model was taken
 * on and the best approach to JAXB compatibility assessed based on their own merits. In any case, the JAXB compliance
 * unit tests in this project will provide assurance that each type transported over the WS remains functional.
 * 
 * @author aphillips
 */
@WebService
public interface ISchedulerService {
  /** @see IScheduler#createJob(String, Class, java.util.Map, org.pentaho.platform.api.scheduler2.JobTrigger) */
  public String createSimpleJob( String jobName,
      @XmlJavaTypeAdapter( JobParamsAdapter.class ) Map<String, ParamValue> jobParams, SimpleJobTrigger trigger )
    throws SchedulerException;

  /** @see IScheduler#createJob(String, Class, java.util.Map, org.pentaho.platform.api.scheduler2.JobTrigger) */
  public String createComplexJob( String jobName,
      @XmlJavaTypeAdapter( JobParamsAdapter.class ) Map<String, ParamValue> jobParams, ComplexJobTrigger trigger )
    throws SchedulerException;

  /** @see IScheduler#updateJob(String, java.util.Map, org.pentaho.platform.api.scheduler2.JobTrigger) */
  public void updateJobToUseSimpleTrigger( String jobId,
      @XmlJavaTypeAdapter( JobParamsAdapter.class ) Map<String, ParamValue> jobParams, SimpleJobTrigger trigger )
    throws SchedulerException;

  /** @see IScheduler#updateJob(String, String, java.util.Map, org.pentaho.platform.api.scheduler2.JobTrigger) */
  public String updateJobSimpleTriggerWithJobName( String jobName, String jobId,
      @XmlJavaTypeAdapter( JobParamsAdapter.class ) Map<String, ParamValue> jobParams, SimpleJobTrigger trigger )
          throws SchedulerException;

  /** @see IScheduler#updateJob(String, java.util.Map, org.pentaho.platform.api.scheduler2.JobTrigger) */
  public void updateJobToUseComplexTrigger( String jobId,
      @XmlJavaTypeAdapter( JobParamsAdapter.class ) Map<String, ParamValue> jobParams, ComplexJobTrigger trigger )
    throws SchedulerException;

  /** @see IScheduler#updateJob(String, String, java.util.Map, org.pentaho.platform.api.scheduler2.JobTrigger) */
  public String updateJobComplexTriggerWithJobName( String jobName, String jobId,
      @XmlJavaTypeAdapter( JobParamsAdapter.class ) Map<String, ParamValue> jobParams, ComplexJobTrigger trigger )
          throws SchedulerException;

  /** @see IScheduler#removeJob(String) */
  public void removeJob( String jobId ) throws SchedulerException;

  /** @see IScheduler#pauseJob(String) */
  public void pauseJob( String jobId ) throws SchedulerException;

  /** @see IScheduler#resumeJob(String) */
  public void resumeJob( String jobId ) throws SchedulerException;

  /** @see IScheduler#pause() */
  public void pause() throws SchedulerException;

  /** @see IScheduler#start() */
  public void start() throws SchedulerException;

  /** @see IScheduler#getJobs(org.pentaho.platform.api.scheduler2.IJobFilter) */
  @XmlJavaTypeAdapter( JobAdapter.class )
  public Job[] getJobs() throws SchedulerException;

  /**
   * Returns the scheduler status.
   * 
   * @return the ordinal value of the current scheduler state
   * @see IScheduler#getStatus()
   */
  public int getSchedulerStatus() throws SchedulerException;

  default public  boolean canStopScheduler (){
    return true;
  }
}
