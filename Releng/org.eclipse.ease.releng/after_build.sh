#!/bin/bash

targetUpdateSiteLocation=/home/data/httpd/download.eclipse.org/e4/ease/update_site/${JOB_NAME}/
sourceUpdateSiteLocation=${WORKSPACE}/Releng/org.eclipse.ease.releng.p2/target/repository
#Clear all build
echo "Clearing $targetUpdateSiteLocation"
rm -rf ${targetUpdateSiteLocation}/*
#Moving generated update site
echo "Moving $sourceUpdateSiteLocation to $targetUpdateSiteLocation"
mv ${sourceUpdateSiteLocation}* ${targetUpdateSiteLocation}
