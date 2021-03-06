-- phpMyAdmin SQL Dump
-- version 5.0.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jul 06, 2021 at 07:06 PM
-- Server version: 10.4.17-MariaDB
-- PHP Version: 8.0.2

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `management_db`
--

-- --------------------------------------------------------

--
-- Stand-in structure for view `all_orders`
-- (See below for the actual view)
--
CREATE TABLE `all_orders` (
`user_id` int(11)
,`username` varchar(255)
,`contact_no` varchar(15)
,`date` varchar(278)
,`order_id` int(20)
,`address` varchar(255)
,`qty` int(11)
,`sell_price` float
,`sub_total` double
,`state` varchar(255)
,`request` text
);

-- --------------------------------------------------------

--
-- Table structure for table `category`
--

CREATE TABLE `category` (
  `category_id` int(11) NOT NULL,
  `category_name` varchar(255) NOT NULL,
  `category_note` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `category`
--

INSERT INTO `category` (`category_id`, `category_name`, `category_note`) VALUES
(5, 'food', 'delicious'),
(6, 'sweet', 'yummy!'),
(18, 'Milk Tea', 'Milk na Tea, Tea na Milk');

-- --------------------------------------------------------

--
-- Table structure for table `customer_order`
--

CREATE TABLE `customer_order` (
  `order_id` int(20) NOT NULL,
  `buyer_id` int(11) NOT NULL,
  `state` varchar(255) NOT NULL,
  `address` varchar(255) NOT NULL,
  `request` varchar(255) NOT NULL,
  `phone` varchar(255) NOT NULL,
  `qty` int(11) NOT NULL,
  `order_date` datetime NOT NULL,
  `delivered_by` int(11) NOT NULL DEFAULT 0,
  `date_delivered` timestamp NULL DEFAULT NULL,
  `order_no` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `customer_order`
--

INSERT INTO `customer_order` (`order_id`, `buyer_id`, `state`, `address`, `request`, `phone`, `qty`, `order_date`, `delivered_by`, `date_delivered`, `order_no`) VALUES
(1, 4, 'cancelled', '14.287049,121.107627', '', '0912 3456 789', 0, '2021-06-15 23:02:35', 7, '2021-07-04 11:21:32', '4210615230219'),
(2, 4, 'received', '14.282611,121.104670', '', '0912 3456 789', 0, '2021-06-16 20:43:14', 7, '2021-07-04 08:32:48', '4210616204135'),
(3, 4, 'received', '14.287540,121.093966', '', '0912 3456 789', 0, '2021-07-04 19:27:50', 7, '2021-07-04 11:29:43', '4210704192728'),
(4, 4, 'delivering', '14.275612,121.084418', '', '0912 3456 789', 0, '2021-07-04 19:47:36', 7, '2021-07-04 11:48:09', '4210704194718'),
(5, 4, 'wait_deliver', '14.296275,121.099665', '', '0912 3456 789', 0, '2021-07-04 21:12:39', 0, NULL, '4210704211228');

-- --------------------------------------------------------

--
-- Stand-in structure for view `delivering_orders`
-- (See below for the actual view)
--
CREATE TABLE `delivering_orders` (
`user_id` int(11)
,`username` varchar(255)
,`contact_no` varchar(15)
,`date` datetime
,`order_id` int(20)
,`product_id` int(11)
,`product_img` varchar(255)
,`product_name` varchar(255)
,`address` varchar(255)
,`qty` int(11)
,`sell_price` float
,`sub_total` double
,`state` varchar(255)
,`request` text
,`delivered_by` int(11)
);

-- --------------------------------------------------------

--
-- Table structure for table `driver`
--

CREATE TABLE `driver` (
  `driver_id` int(11) NOT NULL,
  `driver_name` varchar(255) NOT NULL,
  `driver_email` varchar(255) NOT NULL,
  `driver_phone` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `driver`
--

INSERT INTO `driver` (`driver_id`, `driver_name`, `driver_email`, `driver_phone`) VALUES
(1, 'ana', 'ana@gmail.com', '093812316'),
(6, 'benben', 'ben@gmail.com', '0912312'),
(7, 'lara', 'lara@gmail.com', '091231231'),
(9, 'mimi', 'mimi@gmail.com', '092131881');

-- --------------------------------------------------------

--
-- Table structure for table `driver_location`
--

CREATE TABLE `driver_location` (
  `driver_location_id` int(11) NOT NULL,
  `delivered_by` int(11) NOT NULL,
  `order_id` int(11) NOT NULL,
  `latitude` decimal(11,8) NOT NULL,
  `longitude` decimal(11,8) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `driver_location`
--

INSERT INTO `driver_location` (`driver_location_id`, `delivered_by`, `order_id`, `latitude`, `longitude`) VALUES
(13, 7, 4, '14.30791170', '121.08920570');

-- --------------------------------------------------------

--
-- Table structure for table `feedback`
--

CREATE TABLE `feedback` (
  `feedback_id` int(11) NOT NULL,
  `order_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `feedback_desc` text NOT NULL,
  `feedback_date` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `feedback`
--

INSERT INTO `feedback` (`feedback_id`, `order_id`, `user_id`, `feedback_desc`, `feedback_date`) VALUES
(1, 3, 4, 'test abc', '2021-07-06 16:51:17');

-- --------------------------------------------------------

--
-- Table structure for table `gcash_information`
--

CREATE TABLE `gcash_information` (
  `number` varchar(11) NOT NULL,
  `name` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `gcash_information`
--

INSERT INTO `gcash_information` (`number`, `name`) VALUES
('09123456789', 'Juan dela Cruz');

-- --------------------------------------------------------

--
-- Table structure for table `order_products`
--

CREATE TABLE `order_products` (
  `order_id` int(20) NOT NULL,
  `product_id` int(11) NOT NULL,
  `qty` int(11) NOT NULL,
  `request` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `order_products`
--

INSERT INTO `order_products` (`order_id`, `product_id`, `qty`, `request`) VALUES
(1, 7, 3, ''),
(2, 8, 2, ''),
(3, 7, 1, ''),
(3, 1, 1, ''),
(3, 13, 5, ''),
(3, 9, 1, ''),
(3, 8, 1, ''),
(4, 10, 2, ''),
(5, 7, 2, '');

-- --------------------------------------------------------

--
-- Table structure for table `product`
--

CREATE TABLE `product` (
  `product_id` int(11) NOT NULL,
  `product_category_id` int(11) NOT NULL,
  `market_price` float NOT NULL,
  `sell_price` float NOT NULL,
  `product_code` varchar(255) NOT NULL,
  `product_img` varchar(255) NOT NULL,
  `model` varchar(255) NOT NULL,
  `purchase_description` varchar(255) NOT NULL,
  `product_description` varchar(255) NOT NULL,
  `status` varchar(255) DEFAULT 'notSale',
  `product_name` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `product`
--

INSERT INTO `product` (`product_id`, `product_category_id`, `market_price`, `sell_price`, `product_code`, `product_img`, `model`, `purchase_description`, `product_description`, `status`, `product_name`) VALUES
(1, 18, 80, 80, 'HKKD', 'boba.jpg', 'S', 'Hokkaido Milktea', 'Neque porro quisquam est qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit...', 'notSale', 'Hokkaido Milk Tea'),
(2, 18, 90, 90, 'HKKD', 'boba.jpg', 'M', 'Neque porro quisquam est qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit...', 'Neque porro quisquam est qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit...', 'notSale', 'Hokkaido Milk Tea'),
(3, 18, 100, 100, 'HKKD', 'boba.jpg', 'L', 'Neque porro quisquam est qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit...', 'Neque porro quisquam est qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit...', 'notSale', 'Hokkaido Milk Tea'),
(4, 18, 80, 80, 'WNTRMLN', 'ph_bubble_tea_diy.jpg', 'S', 'Neque porro quisquam est qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit...', 'Neque porro quisquam est qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit...', 'notSale', 'Wintermelon'),
(5, 18, 90, 90, 'WNTRMLN', 'ph_bubble_tea_diy.jpg', 'M', 'Neque porro quisquam est qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit...', 'Neque porro quisquam est qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit...', 'notSale', 'Wintermelon'),
(6, 18, 100, 100, 'WNTRMLN', 'ph_bubble_tea_diy.jpg', 'L', 'Neque porro quisquam est qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit...', 'Neque porro quisquam est qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit...', 'notSale', 'Wintermelon'),
(7, 5, 90, 90, 'KRCD', 'plain-corndog.jpg', 'Plain', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Suspendisse a.', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Suspendisse a.', 'notSale', 'Korean Corndog'),
(8, 5, 90, 90, 'KRCD', 'potato-corndog.jpg', 'Potato', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Suspendisse a.', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Suspendisse a.', 'notSale', 'Korean Corndog'),
(9, 5, 300, 300, 'CKNWNG', 'buffalo-wings.jpg', 'Buffalo', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Suspendisse a.', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Suspendisse a.', 'notSale', 'Chicken Wings'),
(10, 5, 300, 300, 'CKNWNG', 'garlic-parm-wings.jpg', 'Garlic Parmesan', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Suspendisse a.', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Suspendisse a.', 'notSale', 'Chicken Wings'),
(11, 5, 300, 300, 'CKNWNG', 'korean-wings.jpg', 'Korean', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Suspendisse a.', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Suspendisse a.', 'notSale', 'Chicken Wings'),
(12, 6, 150, 150, 'CHSCK', 'classic-cheesecake.jpg', 'Classic', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Suspendisse a.', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Suspendisse a.', 'notSale', 'Cheesecake'),
(13, 6, 150, 150, 'CHSCK', 'strawberry-cheesecake.jpg', 'Strawberry', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Suspendisse a.', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Suspendisse a.', 'notSale', 'Cheesecake'),
(14, 6, 150, 150, 'CHSCK', 'oreo-cheesecake.jpg', 'Oreo', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Suspendisse a.', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Suspendisse a.', 'notSale', 'Cheesecake'),
(15, 6, 150, 150, 'CHSCK', 'mango-cheesecake.jpg', 'Mango', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Suspendisse a.', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Suspendisse a.', 'notSale', 'Cheesecake'),
(16, 18, 90, 80, 'BLKPRL', 'boba.jpg', 'S', 'BLKPRL', 'BLKPRL', 'notSale', 'BLKPRL');

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `id` int(11) NOT NULL,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `contact_no` varchar(15) NOT NULL,
  `accesslevel` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`id`, `username`, `password`, `email`, `contact_no`, `accesslevel`) VALUES
(0, 'initial', 'initial', 'initial@initial.com', '', 'initial'),
(1, 'aughus', 'bueno', 'aughusbueno@gmail.com', '09123456789', 'customer'),
(3, 'admin', 'admin', 'jules@gmail.com', '09123456789', 'admin'),
(4, 'abc', 'abc', 'raffytulfoinaction@gmail.com', '0912 3456 789', 'customer'),
(6, 'luffy', 'luffy', 'futurepirateking@onepiece.com', '0912 3456 789', 'customer'),
(7, '123', '123', '123@123.com', '0912 3456 789', 'driver'),
(9, 'kardo', 'kardo', 'kardo@pulis.com', '0911 9119 911', 'driver'),
(11, 'new', 'new', 'new@new.com', '', 'admin'),
(12, 'qwe', 'qwe', 'qwe', '0912 3456 789', 'customer');

-- --------------------------------------------------------

--
-- Stand-in structure for view `wait_driver_orders`
-- (See below for the actual view)
--
CREATE TABLE `wait_driver_orders` (
`user_id` int(11)
,`username` varchar(255)
,`contact_no` varchar(15)
,`date` datetime
,`order_id` int(20)
,`product_id` int(11)
,`product_img` varchar(255)
,`product_name` varchar(255)
,`address` varchar(255)
,`qty` int(11)
,`sell_price` float
,`sub_total` double
,`state` varchar(255)
,`request` text
);

-- --------------------------------------------------------

--
-- Structure for view `all_orders`
--
DROP TABLE IF EXISTS `all_orders`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `all_orders`  AS SELECT `user`.`id` AS `user_id`, `user`.`username` AS `username`, `user`.`contact_no` AS `contact_no`, concat(`co`.`order_date`,' (',`co`.`state`,') ') AS `date`, `co`.`order_id` AS `order_id`, `co`.`address` AS `address`, `op`.`qty` AS `qty`, `prod`.`sell_price` AS `sell_price`, `prod`.`sell_price`* `op`.`qty` AS `sub_total`, `co`.`state` AS `state`, `op`.`request` AS `request` FROM (((`order_products` `op` left join `customer_order` `co` on(`op`.`order_id` = `co`.`order_id`)) left join `product` `prod` on(`op`.`product_id` = `prod`.`product_id`)) left join `user` on(`co`.`buyer_id` = `user`.`id`)) ;

-- --------------------------------------------------------

--
-- Structure for view `delivering_orders`
--
DROP TABLE IF EXISTS `delivering_orders`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `delivering_orders`  AS SELECT `user`.`id` AS `user_id`, `user`.`username` AS `username`, `user`.`contact_no` AS `contact_no`, `co`.`order_date` AS `date`, `co`.`order_id` AS `order_id`, `op`.`product_id` AS `product_id`, `prod`.`product_img` AS `product_img`, `prod`.`product_name` AS `product_name`, `co`.`address` AS `address`, `op`.`qty` AS `qty`, `prod`.`sell_price` AS `sell_price`, `prod`.`sell_price`* `op`.`qty` AS `sub_total`, `co`.`state` AS `state`, `op`.`request` AS `request`, `co`.`delivered_by` AS `delivered_by` FROM (((`order_products` `op` left join `customer_order` `co` on(`op`.`order_id` = `co`.`order_id`)) left join `product` `prod` on(`op`.`product_id` = `prod`.`product_id`)) left join `user` on(`co`.`buyer_id` = `user`.`id`)) WHERE `co`.`state` = 'delivering' ;

-- --------------------------------------------------------

--
-- Structure for view `wait_driver_orders`
--
DROP TABLE IF EXISTS `wait_driver_orders`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `wait_driver_orders`  AS SELECT `user`.`id` AS `user_id`, `user`.`username` AS `username`, `user`.`contact_no` AS `contact_no`, `co`.`order_date` AS `date`, `co`.`order_id` AS `order_id`, `op`.`product_id` AS `product_id`, `prod`.`product_img` AS `product_img`, `prod`.`product_name` AS `product_name`, `co`.`address` AS `address`, `op`.`qty` AS `qty`, `prod`.`sell_price` AS `sell_price`, `prod`.`sell_price`* `op`.`qty` AS `sub_total`, `co`.`state` AS `state`, `op`.`request` AS `request` FROM (((`order_products` `op` left join `customer_order` `co` on(`op`.`order_id` = `co`.`order_id`)) left join `product` `prod` on(`op`.`product_id` = `prod`.`product_id`)) left join `user` on(`co`.`buyer_id` = `user`.`id`)) WHERE `co`.`state` = 'wait_deliver' ;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `category`
--
ALTER TABLE `category`
  ADD PRIMARY KEY (`category_id`);

--
-- Indexes for table `customer_order`
--
ALTER TABLE `customer_order`
  ADD PRIMARY KEY (`order_id`);

--
-- Indexes for table `driver_location`
--
ALTER TABLE `driver_location`
  ADD PRIMARY KEY (`driver_location_id`),
  ADD UNIQUE KEY `delivered_by_2` (`delivered_by`,`order_id`),
  ADD KEY `order_id` (`order_id`),
  ADD KEY `delivered_by` (`delivered_by`);

--
-- Indexes for table `feedback`
--
ALTER TABLE `feedback`
  ADD PRIMARY KEY (`feedback_id`);

--
-- Indexes for table `product`
--
ALTER TABLE `product`
  ADD PRIMARY KEY (`product_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `category`
--
ALTER TABLE `category`
  MODIFY `category_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;

--
-- AUTO_INCREMENT for table `customer_order`
--
ALTER TABLE `customer_order`
  MODIFY `order_id` int(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `driver_location`
--
ALTER TABLE `driver_location`
  MODIFY `driver_location_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- AUTO_INCREMENT for table `feedback`
--
ALTER TABLE `feedback`
  MODIFY `feedback_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `product`
--
ALTER TABLE `product`
  MODIFY `product_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
