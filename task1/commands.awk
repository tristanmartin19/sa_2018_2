# FS = built in variable Field Separator 
# OFS = Output Field Separator -> otherwise lines with deleted "yes" are printed with " " als delimiter  ...
# ... [cf. https://unix.stackexchange.com/questions/222134/delimiter-is-being-changed-with-fields].

# '$4 !=""' -> Column "name" must not be empty, requirement in task 1.

# 'for (i=5; i<=NF; i++)' -> in all columns from the 5th one, "yes" is deleted, NOT in the "name" columns, ... 
# ... because a shop could theoretically be called "yes".
# NF = built-in varialbe Number Of Fields

# RegEX '^yes$' -> only whole words "yes" are deleted, not e.g. "yes" in "yesss.at".

# Start with awk -f command.awk shops-graz.csv

# awk inline: all(!) parts of the awk command in single quotes!: 'BEGIN{...}' '...' '{...}'


BEGIN {FS=OFS="\t"; print "CREATE DATABASE IF NOT EXISTS shop_finder_db; USE shop_finder_db; DROP TABLE IF EXISTS shops; CREATE TABLE shops (id MEDIUMINT NOT NULL AUTO_INCREMENT, osm_id BIGINT NOT NULL, longitude double NOT NULL, latitude double NOT NULL, name varchar(255) NOT NULL, category varchar(255), homepage varchar(255), PRIMARY KEY (id)); DROP TABLE IF EXISTS points_of_interest; CREATE TABLE points_of_interest (id MEDIUMINT NOT NULL AUTO_INCREMENT, osm_id varchar(11) NOT NULL, longitude double NOT NULL, latitude double NOT NULL, name varchar(255) NOT NULL, PRIMARY KEY (id));"}$4 !=""{for (i=5; i<=NF; i++){gsub(/^yes$/,"", $i)}; print "INSERT INTO shops (osm_id, longitude, latitude, name, category, homepage) VALUES ("$1","$2","$3",\""$4"\",\""$5"\",\""$6"\");"}END {print "LOAD DATA LOCAL INFILE 'graz_poi_complete.csv' INTO TABLE points_of_interest FIELDS TERMINATED BY '|' ENCLOSED BY '' LINES TERMINATED BY '\\r\\n' IGNORE 1 ROWS (osm_id, longitude, latitude, name) SET id = NULL;"}