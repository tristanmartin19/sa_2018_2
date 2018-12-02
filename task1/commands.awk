# Start with awk -f command.awk shops-graz.csv

BEGIN 	{       ### CONSTANTS        
                        FS=OFS="\t" 	# FS := built-in variable Field Separator, OFS := Output Field Separator
                        categories[""] = "NULL"
                        
                        ###  DATABASES                        
                        print "DROP DATABASE IF EXISTS sa_shop_finder;"
                        print "CREATE DATABASE sa_shop_finder CHARACTER SET utf8 COLLATE utf8_general_ci;"
                        print "USE sa_shop_finder;"                        
                        
                        ### DATABASE SCHEMA ##
                        #                        
                        ## Table 'shops' ##
                        print "DROP TABLE IF EXISTS shops;"			
                        print "CREATE TABLE shops (shop_id MEDIUMINT NOT NULL, osm_id BIGINT NOT NULL, longitude double NOT NULL, latitude double NOT NULL, name varchar(255) NOT NULL, homepage varchar(255), PRIMARY KEY (shop_id));" 
                        
                        ## Table 'shop_categories' ##
                        print "DROP TABLE IF EXISTS shop_categories;"
                        print "CREATE TABLE shop_categories (category_id MEDIUMINT NOT NULL, category_name varchar(255) NOT NULL UNIQUE, PRIMARY KEY (category_id));"
                        
                        ## Table 'shop_has_categories' ##
                        print "DROP TABLE IF EXISTS shop_has_categories;" 
                        print "CREATE TABLE shop_has_categories (id MEDIUMINT NOT NULL AUTO_INCREMENT, shop_id MEDIUMINT NOT NULL, category_id MEDIUMINT NOT NULL, UNIQUE(shop_id, category_id), PRIMARY KEY (id));" 
                        
                        ## Table 'points_of_interest' ##
                        print "DROP TABLE IF EXISTS points_of_interest;"
                        print "CREATE TABLE points_of_interest (poi_id MEDIUMINT NOT NULL AUTO_INCREMENT, osm_id varchar(11) NOT NULL, longitude double NOT NULL, latitude double NOT NULL, name varchar(255) NOT NULL, PRIMARY KEY (poi_id));"
			
			 
                }
$4 !=""	{ # Column "name" must not be empty, requirement in task 1.
        
                        ### DELETING "yes" ENTRIES                        
                        # Deletes "yes"/"no" in all columns from the 5th one, NOT in the "name" column, because a shop could theoretically be called yes"/"no".	
                        for (i = 5; i <= NF; i++){gsub(/^yes$|^no$/,"", $i)} # NF := built-in varialbe Number Of Fields.                        
                        
                        ### POPULATION OF DATABASE ###
                        
                        ## Table shops ##	
                        print "INSERT INTO shops VALUES ("NR","$1","$2","$3",\""$4"\",\""$6"\");"# NR = Nubmer of Row

                        ## Table 'shop_has_categories' ##			
                        # Creates an associative array with unique shop categories as keys and an index.	
                        split($5, shop_field, /; */) # Split shop categories with two or more entries separeted by a semicolon, e.g. "photo_studio; copyshop"
                        for (i in shop_field) {
				if (!(shop_field[i]  ~ /^yes$|^no$/)) { # Filter out "yes" or "no" as categories. Some categorie entries are e.g. "yes;photo_studio;copyshop"
					if (!(shop_field[i] in categories)){categories[shop_field[i]] = ++j}
					print "INSERT INTO shop_has_categories (shop_id, category_id) VALUES ("NR", "categories[shop_field[i]]");"
				}				
                        }# category field not empty and not in array yet		                        
                }
END 	{
                        ## Table points_of_interest ##			
                        print "LOAD DATA LOCAL INFILE 'graz_poi_complete.csv' INTO TABLE points_of_interest FIELDS TERMINATED BY '|' ENCLOSED BY '' LINES TERMINATED BY '\\r\\n' IGNORE 1 ROWS (osm_id, longitude, latitude, name) SET poi_id = NULL;"
			
                        # Table shop_categories
                        delete categories[""]
                        for (element in categories)  print "INSERT INTO shop_categories VALUES ("categories[element]",\"" element "\");"			
			
			### UNDEFINED ROWS
                        
                        ### MODIFYING DATABASE SCHEME AFTER POPULATING WITH DATA FROM PIPE FOR FUTURE CRUD OPERATIONS ###	                        
                       
                        print "ALTER TABLE shops MODIFY shop_id MEDIUMINT NOT NULL AUTO_INCREMENT;"
                        print "ALTER TABLE shop_categories MODIFY category_id MEDIUMINT NOT NULL AUTO_INCREMENT;"
						
                         # FOREIGN KEY constraint fails in CREATE TABLE shop_has_categories ... statement because table shop_categories doesn't
                        # exist yet when shop_has_categories table is populated
                        print "ALTER TABLE shop_has_categories ADD FOREIGN KEY (shop_id) REFERENCES shops (shop_id);"
                        print "ALTER TABLE shop_has_categories ADD FOREIGN KEY (category_id) REFERENCES shop_categories (category_id);" 
			
			### UNDEFINED ROWS in tables shop_categories and points_of_interest
			print "INSERT INTO points_of_interest (osm_id, longitude, latitude, name) VALUES ('', 0, 0, 'undefined');" 
			print "INSERT INTO shop_categories (category_name) VALUES ('undefined');"

			### EMPTY TABLE  'favourites' ###
			 print "DROP TABLE IF EXISTS favourites;"
			 print "CREATE TABLE favourites (favourite_id MEDIUMINT NOT NULL AUTO_INCREMENT, name varchar(255) NOT NULL, category_id MEDIUMINT, searched_shop_name varchar(255), poi_id MEDIUMINT, distance_to_poi MEDIUMINT, PRIMARY KEY (favourite_id), FOREIGN KEY (category_id) REFERENCES shop_categories (category_id), FOREIGN KEY (poi_id) REFERENCES points_of_interest (poi_id));"
                }