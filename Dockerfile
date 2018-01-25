# Source: https://github.com/openshift/jenkins/blob/master/2/Dockerfile.rhel7
FROM registry.access.redhat.com/openshift3/jenkins-2-rhel7

MAINTAINER Chris Troeger <Christopher.Troeger@iteratec.de>

# Copy config file templates
RUN mkdir /var/lib/jenkins/configFileTemplates
COPY configuration/configFileTemplates/* /var/lib/jenkins/configFileTemplates/

# Copy post-init scripts
RUN mkdir /var/lib/jenkins/init.groovy.d
COPY configuration/init.groovy.d/* /var/lib/jenkins/init.groovy.d/