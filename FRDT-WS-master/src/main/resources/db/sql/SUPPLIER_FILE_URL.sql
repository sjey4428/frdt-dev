
CREATE TABLE `SUPPLIER_FILE_URL` (
  `uuid` varchar(128) NOT NULL,
  `file_url` varchar(128) NOT NULL,
  PRIMARY KEY (`uuid`,`file_url`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
