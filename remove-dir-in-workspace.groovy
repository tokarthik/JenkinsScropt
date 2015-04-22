// Licensed under MIT
// author : Damien Nozay
// ---------------------------------------------------------
// This script cleans a subdir in all existing workspaces for a selected job.
// node -> check workspace -> check subdir -> delete
// ---------------------------------------------------------

job = Jenkins.instance.getItemByFullName('SomeJobFolder/myJob')
subdir = 'dist'

println "Looking for job: " + job.fullName

for (node in Jenkins.instance.getNodes()) {
  workspacePath = node.getWorkspaceFor(job)
  if (workspacePath != null && workspacePath.exists()) {
    println "* found workspace on '" + node.getSelfLabel().toString() + "' (" + node.getAssignedLabels().join(",") + ")"
    targetDir = workspacePath.child(subdir)
    if (targetDir.exists()) {
      println "  * found target directory"
      if (!job.isBuilding()) {
        println "  * removing directory (job is not building)"
        targetDir.deleteRecursive()
      } else {
        println "  * not removing directory (job is building)"
      }
    }
  }
}
