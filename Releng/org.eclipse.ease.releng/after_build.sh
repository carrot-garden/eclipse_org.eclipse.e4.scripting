#!/bin/bash
#--------------------------------------------------------------------------------
# Copyright (c) 2013 Atos.
#
#    
# All rights reserved. This program and the accompanying materials
# are made available under the terms of the Eclipse Public License v1.0
# which accompanies this distribution, and is available at
# http://www.eclipse.org/legal/epl-v10.html
#
# Contributors:
#    Arthur Daussy (Atos)
#--------------------------------------------------------------------------------

targetUpdateSiteLocation = /home/data/httpd/download.eclipse.org/e4/ease/${JOB_NAME}/
sourceUpdateSiteLocation = ${WORKSPACE}/Releng/org.eclipse.ease.releng.p2/target/repository
#Clear all build
echo "Clearing $sourceUpdateSiteLocation"
rm -rf $targetUpdateSiteLocation
#Moving generated update site
echo "Moving $sourceUpdateSiteLocation to $targetUpdateSiteLocation"
mv $sourceUpdateSiteLocation/* $targetUpdateSiteLocation