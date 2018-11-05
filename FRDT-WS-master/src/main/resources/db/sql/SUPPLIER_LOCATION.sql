
CREATE TABLE `SUPPLIER_LOCATION` (
  `ship_point_no` int(11) NOT NULL,
  `uuid` varchar(128) NOT NULL,
  `physical_address_zone` varchar(128) DEFAULT NULL,
  `city` varchar(30) DEFAULT NULL,
  `state` varchar(30) DEFAULT NULL,
  `zip_code` varchar(30) DEFAULT NULL,
  `dc_plant` varchar(30) DEFAULT NULL,
  `sourced_product` varchar(1) DEFAULT NULL,
  `rail_facilities` varchar(1) DEFAULT NULL,
  `drop_trailer` varchar(1) DEFAULT NULL,
  `created_date` date DEFAULT NULL,
  `created_time` varchar(8) DEFAULT NULL,
  `modified_date` date DEFAULT NULL,
  `modified_time` varchar(8) DEFAULT NULL,
  `modified_by` varchar(30) DEFAULT NULL,
  `effective_date` date DEFAULT NULL,
  `deleted` varchar(1) DEFAULT NULL,
   `pickup_allowance_uom` varchar(30) DEFAULT NULL,
  `freight_rate_uom` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`ship_point_no`,`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
