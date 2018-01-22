FROM openshift/jenkins:latest

USER jenkins

# Disable Setup Wizard
ENV JAVA_OPTS="-Djenkins.install.runSetupWizard=false"

#  Configure Proxy for PluginManager
#TODO Check if this works already during Jenkins startup
#COPY src/configure-proxy-for-pluginmanager.groovy /usr/share/jenkins/ref/init.groovy.d/configure-proxy-for-pluginmanager.groovy

# Install plugins
COPY resources/plugins.txt /usr/share/jenkins/plugins.txt
RUN /usr/local/bin/install-plugins.sh < /usr/share/jenkins/plugins.txt

# Configure "Globale Credentials"
COPY src/configure-global-credentials.groovy /usr/share/jenkins/ref/init.groovy.d/configure-global-credentials.groovy

# Configure "Managed files"
COPY src/configure-managed-files.groovy /usr/share/jenkins/ref/init.groovy.d/configure-managed-files.groovy

# Configure "In-process Script Approval"
COPY src/configure-in-process-script-approval.groovy /usr/share/jenkins/ref/init.groovy.d/configure-in-process-script-approval.groovy

# Configure "Global Tool Configuration"
COPY src/configure-global-tools.groovy /usr/share/jenkins/ref/init.groovy.d/configure-global-tools.groovy

# Configure Jenkins itself
COPY src/configure-jenkins-itself.groovy /usr/share/jenkins/ref/init.groovy.d/configure-jenkins-itself.groovy

