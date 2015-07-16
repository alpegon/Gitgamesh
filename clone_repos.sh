#!/bin/bash
shopt -s extglob
declare -a REPOS=("https://github.com/sshirokov/stltwalker" "https://github.com/hippich/stl" "https://github.com/brickify/stl-models" \
"https://github.com/walterhsiao/models" "https://github.com/tpalmer/stl-models")
echo "cloning ${#REPOS[@]} repos..."
for repo in "${REPOS[@]}"
do
	folder=$(echo $repo | awk -F'/' '{print $4}')
	git clone $repo /home/gitgamesh/git/$folder
done
find /home/gitgamesh/git/ -type f ! \( -name "*.stl" -o -name "*.jpg" \) -delete
find /home/gitgamesh/git/ -name "* *" -type d | rename 's/ /_/g'
 
for repo in $(ls -1 /home/gitgamesh/git/)
do
	rm -rf .git
	find . -type d -empty -delete
	cd /home/gitgamesh/git/$repo
	for f in $(ls -1 .)
		do
			mkdir $f/files
			find $f -type f -name "*.stl" -exec mv '{}' $f/files \;
			find $f -type f -name "*.jpg" -exec mv '{}' $f/files \;
			rm -rf $f/!(files)
			rm -rf .git
			find . -type d -empty -delete
			cd /home/gitgamesh/git/$repo/$f
        		git init  
        		git add .
        		git commit -m "First Commit"
			cd /home/gitgamesh/git/$repo
		done
done

#sudo tree -H prueba /home/gitgamesh/git/ > /opt/apache-tomcat-7.0.63/webapps/ROOT/tree.html
