#!/usr/bin/env bash

# Ref: https://gist.github.com/enriched/b0a685e124a2abff3ac92bf3a5e63630

set -euo pipefail

cd $(bazel info workspace)

WORKSPACE_NAME=$(basename $PWD)

cat << EOF > ./.project
<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<projectDescription>
	<name>${WORKSPACE_NAME}</name>
	<comment/>
	<projects>
	</projects>
	<buildSpec>
		<buildCommand>
			<name>org.eclipse.jdt.core.javabuilder</name>
			<arguments>
			</arguments>
		</buildCommand>
	</buildSpec>
	<natures>
		<nature>org.eclipse.jdt.core.javanature</nature>
	</natures>
</projectDescription>
EOF

JARS=$(find ./bazel-${WORKSPACE_NAME}/external/maven/v1 -iname '*.jar')
SRC_PATHS=$(find . -type d -path '*/src/main/java' -o -path '*/src/test/java')

cat << EOF > ./.classpath
<?xml version="1.0" encoding="UTF-8"?>
<classpath>
	<classpathentry kind="con" path="org.eclipse.jdt.launching.JRE_CONTAINER/org.eclipse.jdt.internal.debug.ui.launcher.StandardVMType/JavaSE-1.8"/>
$( for PATH in $SRC_PATHS; do echo "	<classpathentry kind=\"src\" path=\"${PATH:2}\" />"; done; )
	<classpathentry kind="output" path="java-bin"/>
$( for JAR in $JARS; do echo "	<classpathentry kind=\"lib\" path=\"${JAR:2}\" />"; done; )
</classpath>
EOF
