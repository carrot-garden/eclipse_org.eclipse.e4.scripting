#!/bin/bash

targetUpdateSiteLocation=/home/data/httpd/download.eclipse.org/e4/updates/ease/${JOB_NAME}/
sourceUpdateSiteLocation=${WORKSPACE}/Releng/org.eclipse.ease.releng.p2/target/repository/
#Clear all build
echo "Clearing $targetUpdateSiteLocation"
rm -rf ${targetUpdateSiteLocation}*
#Moving generated update site
echo "Moving $sourceUpdateSiteLocation to $targetUpdateSiteLocation"
cp -R ${sourceUpdateSiteLocation}* ${targetUpdateSiteLocation}

#Publish dicovery
sourceDiscoveryLocation=Plugins/core/org.eclipse.ease.discovery/DiscoveryDefinition.xmi
cp ${sourceDiscoveryLocation}* ${targetUpdateSiteLocation}