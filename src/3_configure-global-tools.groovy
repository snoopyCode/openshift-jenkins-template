import hudson.tasks.Maven.MavenInstallation
import hudson.tools.InstallSourceProperty
import hudson.tools.ToolProperty
import hudson.tools.ToolPropertyDescriptor
import hudson.util.DescribableList
import jenkins.plugins.nodejs.tools.NodeJSInstallation
import jenkins.model.*

//  Maven Installation
println("Setting up Maven installation..")
def mavenDesc = Jenkins.instance.getExtensionList(hudson.tasks.Maven.DescriptorImpl.class)[0]
def mavenProperty = new InstallSourceProperty()
def mavenAutoInstaller = new hudson.tasks.Maven.MavenInstaller("3.5.0")
mavenProperty.installers.add(mavenAutoInstaller)
def mavenProperties = new DescribableList<ToolProperty<?>, ToolPropertyDescriptor>()
mavenProperties.add(mavenProperty)
def mavenInstallation = new MavenInstallation("Maven 3.5.0", "", mavenProperties)
mavenDesc.setInstallations(mavenInstallation)
mavenDesc.save()

// NodeJS Installation
println("Setting up NodeJS installation..")
def nodeJsDesc = Jenkins.getInstance().getDescriptor("jenkins.plugins.nodejs.tools.NodeJSInstallation")
def nodeJsInstaller = new jenkins.plugins.nodejs.tools.NodeJSInstaller("NodeJS 8.7.0", "", 72)
def nodeJsProperties = new InstallSourceProperty([nodeJsInstaller])
def nodeJsInstallation = new NodeJSInstallation("NodeJs 8.7.0", "", [nodeJsProperties])
nodeJsDesc.setInstallations(nodeJsInstallation)
nodeJsDesc.save()