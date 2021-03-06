import jenkins.model.*
import jenkins.plugins.nodejs.configfiles.NPMConfig
import org.jenkinsci.plugins.configfiles.maven.*
import org.jenkinsci.plugins.configfiles.maven.security.*

def store = Jenkins.instance.getExtensionList('org.jenkinsci.plugins.configfiles.GlobalConfigFiles')[0]

// NPM Config
println("Creating \"npm-config\"...")
String npmConfigContent = new File("/var/lib/jenkins/configFileTemplates/npm-config.txt").text
def npmConfig = new NPMConfig(
        "npm-config",
        "npmrc",
        "npmrc",
        npmConfigContent,
        "",
        null)
store.save(npmConfig)

// NPM Nexus Config
println("Creating \"npm-nexus-config\"...")
String npmNexusConfigContent = new File("/var/lib/jenkins/configFileTemplates/npm-nexus-config.txt").text
def npmNexusConfig = new NPMConfig(
        "npm-nexus-config",
        "NPM Nexus deployment config",
        "NPM Nexus deployment config",
        npmNexusConfigContent,
        "",
        null)
store.save(npmNexusConfig)

// Maven Settings
println("Creating \"maven-settings\"...")
String mavenSettingsContent = new File("/var/lib/jenkins/configFileTemplates/maven-settings.txt").text
def mavenSettings = new MavenSettingsConfig(
        "maven-settings",
        "Maven Settings",
        "maven settings",
        mavenSettingsContent,
        false,
        null)
store.save(mavenSettings)