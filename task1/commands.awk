# Start with awk -f command.awk shops-graz.csv

BEGIN 	{
	FS=OFS="\t" 	# FS := built-in variable Field Separator, OFS := Output Field Separator
	categories[""] = "NULL"
	print "CREATE DATABASE IF NOT EXISTS shop_finder_db;"
	print "USE shop_finder_db;"
	print "DROP TABLE IF EXISTS shops;"
	
	print "CREATE TABLE shops (id MEDIUMINT NOT NULL AUTO_INCREMENT, osm_id BIGINT NOT NULL, longitude double NOT NULL, latitude double NOT NULL, name varchar(255) NOT NULL, category_number MEDIUMINT, homepage varchar(255), PRIMARY KEY (id));"
	
	print "DROP TABLE IF EXISTS points_of_interest;"
	print "CREATE TABLE points_of_interest (id MEDIUMINT NOT NULL AUTO_INCREMENT, osm_id varchar(11) NOT NULL, longitude double NOT NULL, latitude double NOT NULL, name varchar(255) NOT NULL, PRIMARY KEY (id));"
	
	print "DROP TABLE IF EXISTS shop_categories;" 
	print "CREATE TABLE shop_categories (id MEDIUMINT NOT NULL, category varchar(255) NOT NULL, PRIMARY KEY (id));"
	}
	

$4 !=""	{ 		# Column "name" must not be empty, requirement in task 1.
	
	# Deletes "yes" in all columns from the 5th one, NOT in the "name" column, because a shop could theoretically be called "yes".
	# NF := built-in varialbe Number Of Fields.	
	for (i = 5; i <= NF; i++){gsub(/^yes$/,"", $i)}
	
	# Creates an associative array with unique categories als keys and an integers as value. Needed as foreign key in SQL.	
	if (!($5 in categories)){categories[$5] = ++category_id} # category field not empty and not in array yet
	
	print "INSERT INTO shops (osm_id, longitude, latitude, name, category_number, homepage)\nVALUES ("$1","$2","$3",\""$4"\",\""categories[$5]"\",\""$6"\");"
	}
	
	
END 	{
	print "LOAD DATA LOCAL INFILE 'graz_poi_complete.csv' INTO TABLE points_of_interest FIELDS TERMINATED BY '|' ENCLOSED BY '' LINES TERMINATED BY '\\r\\n' IGNORE 1 ROWS (osm_id, longitude, latitude, name) SET id = NULL;"
	
	# print("\nAusgabe des Arrays:") # DEBUG
	delete categories[""]
	for (element in categories)  print "INSERT INTO shop_categories (id, category)\nVALUES(" categories[element] ",\"" element "\");"
		#print element ", " categories[element]
	} # DEBUG