
CREATE TABLE `SUPPLIER_SYSCO_SUVC` (
  `sysco_suvc` varchar(128) NOT NULL,
  `ref_uuid` varchar(64) NOT NULL,
  `deleted` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`sysco_suvc`,`ref_uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
