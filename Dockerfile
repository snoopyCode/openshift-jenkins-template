FROM jenkins/jenkins:lts

USER jenkins

# Disable Setup Wizard
ENV JAVA_OPTS="-Djjenkins.install.runSetupWizard=false"

# TODO Set Proxy at PluginManager

# Install plugins
COPY plugins.txt /usr/share/jenkins/plugins.txt
RUN /usr/local/bin/install-plugins.sh < /usr/share/jenkins/plugins.txt

# TODO Configure "Globale Zugangsdaten"

# TODO Configure "Config File Provider Plugin"

# TODO Configure "In-process Script Approval Plugin"

# TODO Configure "Global Tool Configuration"

# TODO Configure Jenkins itself

