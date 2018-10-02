import org.jenkinsci.plugins.scriptsecurity.scripts.ScriptApproval

// Set approved scripts
println("Configuring in-process script approval..")
def scriptApproval = ScriptApproval.get()
scriptApproval.approveSignature("method com.xpandit.plugins.xrayjenkins.model.ServerConfiguration getServerInstances")
scriptApproval.approveSignature("method com.xpandit.plugins.xrayjenkins.task.XrayExportBuilder getFilePath")
scriptApproval.approveSignature("method com.xpandit.plugins.xrayjenkins.task.XrayImportBuilder getServerInstance")
scriptApproval.approveSignature("method groovy.lang.Binding getVariables")
scriptApproval.approveSignature("method hudson.plugins.git.BranchSpec getName")
scriptApproval.approveSignature("method hudson.plugins.git.GitSCM getBranches")
scriptApproval.approveSignature("method jenkins.model.Jenkins getExtensionList java.lang.String")
scriptApproval.approveSignature("method org.jenkinsci.plugins.workflow.support.steps.build.RunWrapper getRawBuild")
scriptApproval.approveSignature("new com.xpandit.plugins.xrayjenkins.task.XrayExportBuilder com.xpandit.plugins.xrayjenkins.model.XrayInstance java.util.Map")
scriptApproval.approveSignature("new com.xpandit.plugins.xrayjenkins.task.XrayImportBuilder com.xpandit.plugins.xrayjenkins.model.XrayInstance com.xpandit.xray.model.Endpoint java.util.Map")
scriptApproval.approveSignature("new hudson.FilePath java.io.File")
scriptApproval.approveSignature("new hudson.util.LogTaskListener java.util.logging.Logger java.util.logging.Level")
scriptApproval.approveSignature("new java.io.File java.lang.String")
scriptApproval.approveSignature("new java.lang.String byte[]")
scriptApproval.approveSignature("new java.util.HashMap")
scriptApproval.approveSignature("staticField com.xpandit.xray.model.Endpoint CUCUMBER_MULTIPART")
scriptApproval.approveSignature("staticField hudson.model.TaskListener NULL")
scriptApproval.approveSignature("staticField java.util.logging.Level INFO")
scriptApproval.approveSignature("staticMethod java.lang.Thread currentThread")
scriptApproval.approveSignature("staticMethod java.util.logging.Logger getLogger java.lang.String")
scriptApproval.approveSignature("staticMethod jenkins.model.Jenkins getInstance")
scriptApproval.approveSignature("staticMethod org.codehaus.groovy.runtime.EncodingGroovyMethods decodeBase64 java.lang.String")
scriptApproval.approveSignature("method org.jenkinsci.plugins.workflow.support.steps.build.RunWrapper getRawBuild")
scriptApproval.approveSignature("new java.io.File java.lang.String")
scriptApproval.approveSignature("staticMethod jenkins.model.Jenkins getInstance")
scriptApproval.approveSignature("method jenkins.tasks.SimpleBuildStep perform hudson.model.Run hudson.FilePath hudson.Launcher hudson.model.TaskListener")
scriptApproval.approveSignature("method hudson.plugins.git.GitSCM getUserRemoteConfigs")
scriptApproval.approveSignature("method hudson.plugins.git.UserRemoteConfig getUrl")