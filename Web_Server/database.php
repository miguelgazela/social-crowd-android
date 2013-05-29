<?php
    // Connect to the Database
    $db = new PDO('pgsql:host=vdbm;dbname=lbaw12209', 'lbaw12209', 'oN264fc'); //CHANGE
    $db->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
    $db->setAttribute(PDO::ATTR_DEFAULT_FETCH_MODE, PDO::FETCH_ASSOC);
    $db->exec("SET search_path TO sdis"); // Set the schema for this connection.
?>