// author : Ahmed Mubbashir Khan
// Licensed under MIT
// ---------------------------------------------------------
// This script prints out information of last dwonstream job of Upstream job
// e.g. printInformationOfDownstreamJobs("ChangeListner", 11, "All Tests")
// will print all the downstream jobs invodked by ChangeListner build 11 in the view "All Tests"
// ---------------------------------------------------------

import hudson.model.*

//printInformationOfDownstreamJobs("ChangeListner", 11, "All Tests")

def printInformationOfDownstreamJobs(jobName, buildnumber, viewName){
  def build = Jenkins.getInstance().getItemByFullName(jobName).getBuildByNumber(buildnumber)
  println "${build.fullDisplayName} ${build.getCause(hudson.model.Cause.UpstreamCause).upstreamRun}"

  def cause_pattern = /.*${jobName}.*${buildnumber}.*/
  println "Cause pattern: ${cause_pattern}"


  def view = Hudson.instance.getView(viewName)
  def jobsByCause = view.getItems().findAll{job -> job.lastBuild != null &&
    job.lastBuild.getCause(hudson.model.Cause.UpstreamCause)!= null &&
    job.lastBuild.getCause(hudson.model.Cause.UpstreamCause).upstreamRun==~cause_pattern

  }

  jobsByCause.each{ d_build->
   // def d_build = job.lastBuild
    println("Build: ${d_build.lastBuild.fullDisplayName}->"+
     "result:${d_build.lastBuild.result}->${d_build.lastBuild.buildStatusSummary.message}, " +
     "(was triggered by:${d_build.lastBuild.getCause(hudson.model.Cause.UpstreamCause).upstreamRun})" )
  }
}

