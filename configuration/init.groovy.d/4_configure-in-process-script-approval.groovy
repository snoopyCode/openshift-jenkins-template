import org.jenkinsci.plugins.scriptsecurity.scripts.ScriptApproval

// Set approved scripts
println("Configuring in-process script approval..")
def scriptApproval = ScriptApproval.get()
scriptApproval.approveSignature("method hudson.plugins.git.BranchSpec getName")
scriptApproval.approveSignature("method hudson.plugins.git.GitSCM getBranches")
scriptApproval.approveSignature("new java.lang.String byte[]")
scriptApproval.approveSignature("staticMethod org.codehaus.groovy.runtime.EncodingGroovyMethods decodeBase64 java.lang.String")