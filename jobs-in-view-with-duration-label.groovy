// author : Ahmed Mubbashir Khan
// ---------------------------------------------------------
// This script goes through all the jobs in a view, filters succesful and failed jobs seprately
// Then prints outs them along with the time they took
// ---------------------------------------------------------

import hudson.model.*
def str_view = "Pipeline Tests"
def view = Hudson.instance.getView(str_view)
def successfulJobs = view.getItems().findAll{job -> job.lastBuild != null && job.lastBuild.result == hudson.model.Result.SUCCESS}
def faildJobs = view.getItems().findAll{job -> job.lastBuild != null && job.lastBuild.result == hudson.model.Result.FAILURE}
println "Current Successful job:"
successfulJobs.each{job -> println "Job: ${job.name} build on ${job.getAssignedLabelString()} -> took ${job.lastBuild.getDurationString()} to build"}
println "Current Fail job:"
faildJobs.each{job -> println "Job: ${job.name} build on ${job.getAssignedLabelString()} -> took ${job.lastBuild.getDurationString()} to build"}

println "Total jobs: " + view.getItems().size +" Successful: " +successfulJobs.size+ " Failed: " + faildJobs.size
