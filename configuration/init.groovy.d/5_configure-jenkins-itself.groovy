import jenkins.model.Jenkins
import hudson.slaves.EnvironmentVariablesNodeProperty

instance = Jenkins.getInstance()

// Set quiet period to 270 sec
println("Setting quiet period..")
instance.setQuietPeriod(270)

// Add "NO_PROXY" environment variable
println("Adding \"NO_PROXY\" environment variable..")
globalNodeProperties = instance.getGlobalNodeProperties()
envVarsNodePropertyList = globalNodeProperties.getAll(EnvironmentVariablesNodeProperty.class)
newEnvVarsNodeProperty = null
envVars = null
if (envVarsNodePropertyList == null || envVarsNodePropertyList.size() == 0) {
    newEnvVarsNodeProperty = new EnvironmentVariablesNodeProperty();
    globalNodeProperties.add(newEnvVarsNodeProperty)
    envVars = newEnvVarsNodeProperty.getEnvVars()
} else {
    envVars = envVarsNodePropertyList.get(0).getEnvVars()
}
envVars.put("NO_PROXY", "nexus")
instance.save()