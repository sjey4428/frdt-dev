
CREATE TABLE `SUPPLIER_INFO` (
  `effective_date` date DEFAULT NULL,
  `supplier_name` varchar(128) DEFAULT NULL,
  `corporate_address` varchar(128) DEFAULT NULL,
  `supplier_chain_contact` varchar(128) DEFAULT NULL,
  `supplier_chain_manager` varchar(128) DEFAULT NULL,
  `phone` varchar(30) DEFAULT NULL,
  `email` varchar(128) DEFAULT NULL,
  `uuid` varchar(128) NOT NULL,
  `temperature_code` varchar(30) DEFAULT NULL,
  `bid_category` varchar(128) DEFAULT NULL,
  `general_supplier_comments` varchar(128) DEFAULT NULL,
  `product_costing` varchar(30) DEFAULT NULL,
  `lowest_product_cost` varchar(1) DEFAULT NULL,
  `product_uom` varchar(30) DEFAULT NULL,
  `freight_uom` varchar(30) DEFAULT NULL,
  `published_freight_rates` varchar(30) DEFAULT NULL,
  `pickup_allowances` varchar(1) DEFAULT NULL,
  `freight_different` varchar(1) DEFAULT NULL,
  `supplier_comments_freight` varchar(128) DEFAULT NULL,
  `minimum_delivery` varchar(128) DEFAULT NULL,
  `minimum_pickup` varchar(128) DEFAULT NULL,
  `minimum_different` varchar(1) DEFAULT NULL,
  `delivery_pickup_period` varchar(30) DEFAULT NULL,
  `fuel_surcharge_period` varchar(30) DEFAULT NULL,
  `additional_fees` varchar(1) DEFAULT NULL,
  `compliance_shipper_count` varchar(1) DEFAULT NULL,
  `current_pallet_program` varchar(30) DEFAULT NULL,
  `supplier_comments_profile` varchar(128) DEFAULT NULL,
  `truckload_maximum_weight` decimal(9,2) DEFAULT NULL,
  `truckload_maximum_cubes` decimal(9,2) DEFAULT NULL,
  `truckload_maximum_cases` decimal(9,2) DEFAULT NULL,
  `truckload_maximum_pallets` decimal(9,2) DEFAULT NULL,
  `rail_maximum_weight` decimal(9,2) DEFAULT NULL,
  `rail_maximum_cubes` decimal(9,2) DEFAULT NULL,
  `rail_maximum_cases` decimal(9,2) DEFAULT NULL,
  `rail_maximum_pallets` decimal(9,2) DEFAULT NULL,
  `created_date` date DEFAULT NULL,
  `created_time` varchar(8) DEFAULT NULL,
  `modified_date` date DEFAULT NULL,
  `modified_time` varchar(8) DEFAULT NULL,
  `modified_by` varchar(30) DEFAULT NULL,
  `last_uploaded_date` date DEFAULT NULL,
  `last_date_of_full_review` date DEFAULT NULL,
  `next_review_due_date` date DEFAULT NULL,
  `deleted` varchar(1) DEFAULT NULL,
  `created_by` varchar(128) DEFAULT NULL,
  `bracket1_header` varchar(30) DEFAULT NULL,
  `bracket2_header` varchar(30) DEFAULT NULL,
  `bracket3_header` varchar(30) DEFAULT NULL,
  `bracket4_header` varchar(30) DEFAULT NULL,
  `bracket5_header` varchar(30) DEFAULT NULL,
  `bracket6_header` varchar(30) DEFAULT NULL,
  `bracket7_header` varchar(30) DEFAULT NULL,
  `bracket8_header` varchar(30) DEFAULT NULL,
  `brackets_uom` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
