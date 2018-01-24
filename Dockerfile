# Source: https://github.com/openshift/jenkins/blob/master/2/Dockerfile.rhel7
FROM registry.access.redhat.com/openshift3/jenkins-2-rhel7

MAINTAINER Chris Troeger <Christopher.Troeger@iteratec.de>

# Enable override of persisten volume config from image config
ENV OVERRIDE_PV_CONFIG_WITH_IMAGE_CONFIG true

# Copy config file templates
RUN mkdir /opt/openshift/configuration/configFileTemplates
COPY configuration/configFileTemplates/* /opt/openshift/configuration/configFileTemplates/

# Copy post-init scripts
RUN mkdir /opt/openshift/configuration/init.groovy.d
COPY configuration/init.groovy.d/* /opt/openshift/configuration/init.groovy.d/

# FIXME try to create something directly in /var/lib/jenkins
COPY notes.txt /var/lib/jenkins/notes.txt

# Copy config file templates
#RUN mkdir /var/lib/jenkins/configFileTemplates
#COPY configuration/configFileTemplates/* /var/lib/jenkins/configFileTemplates/

# Copy post-init scripts
#RUN mkdir /var/lib/jenkins/init.groovy.d
#COPY configuration/init.groovy.d/* /var/lib/jenkins/init.groovy.d/