-- phpMyAdmin SQL Dump
-- version 5.0.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Apr 02, 2021 at 01:13 PM
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
(15, 'junk foods', 'not healthy for kids'),
(17, 'soup', 'Mushroom');

-- --------------------------------------------------------

--
-- Table structure for table `customer_order`
--

CREATE TABLE `customer_order` (
  `order_id` int(11) NOT NULL,
  `buyer_id` int(11) NOT NULL,
  `state` varchar(255) NOT NULL,
  `address` varchar(255) NOT NULL,
  `request` varchar(255) NOT NULL,
  `phone` varchar(255) NOT NULL,
  `product_id` int(11) NOT NULL,
  `qty` int(11) NOT NULL,
  `date` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `customer_order`
--

INSERT INTO `customer_order` (`order_id`, `buyer_id`, `state`, `address`, `request`, `phone`, `product_id`, `qty`, `date`) VALUES
(2, 1, 'unexamined', 'brgy imelda c.c', 'no cheese', '09367953785', 8, 3, '01-31-2020'),
(3, 1, 'cancel', 'bukid', 'no catsup', '0912312', 8, 1, '01-31-2020'),
(4, 3, 'wait_deliver', 'sa heaven', 'mayo', '09312313', 9, 12, '09-31-2020'),
(5, 4, 'delivering', 'sa impyerno', 'may mani', '092312318', 7, 1, '01-01-2021'),
(6, 4, 'wait_deliver', 'sa tabing ilog', 'madaming sabaw', '0912313', 9, 23, '01-01-2021'),
(7, 3, 'received', 'sa kapitbahay', 'wala', '0938123910', 7, 3, '01-01-2020');

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
-- Table structure for table `feedback`
--

CREATE TABLE `feedback` (
  `feedback_id` int(11) NOT NULL,
  `product_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `feedback_desc` varchar(255) NOT NULL,
  `feedback_date` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `feedback`
--

INSERT INTO `feedback` (`feedback_id`, `product_id`, `user_id`, `feedback_desc`, `feedback_date`) VALUES
(1, 7, 6, 'Good!', '01/26/2021');

-- --------------------------------------------------------

--
-- Table structure for table `order_products`
--

CREATE TABLE `order_products` (
  `order_id` int(11) NOT NULL,
  `product_id` int(11) NOT NULL,
  `qty` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `order_products`
--

INSERT INTO `order_products` (`order_id`, `product_id`, `qty`) VALUES
(4, 16, 5),
(4, 15, 3),
(4, 18, 10),
(6, 15, 15);

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
  `status` varchar(255) DEFAULT NULL,
  `product_name` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `product`
--

INSERT INTO `product` (`product_id`, `product_category_id`, `market_price`, `sell_price`, `product_code`, `product_img`, `model`, `purchase_description`, `product_description`, `status`, `product_name`) VALUES
(7, 15, 80, 90, 'c0en', 'corndog.jpg', 'daoisj', 'ewan basta hotdog', 'hotdog na ma mais', 'sale', 'corndog'),
(8, 15, 800, 900, 'p@nc1t', 'pancit.jpg', 'mod123', 'masarap na pancit ', 'pancit na masarap', 'notSale', 'pancit'),
(9, 17, 20, 30, 'oiap213', 'bugs.jpg', 'dmasdjib', 'bugs na malinis', 'masarap na bugs', ' notSale', 'fried bugs'),
(10, 6, 100, 100, 'c4ke', 'chococak.jpg', 'asdfsad', 'chocolate cake', 'chocolate cake', 'notSale', 'Chocolate Cake'),
(11, 5, 80, 79.99, 'ch33se', 'cheesefries.jpg', 'zxcvqwr', 'cheesy fries', 'cheesy fries', 'notSale', 'Cheesy Fries'),
(12, 15, 35, 29.5, 'kwek2x', 'kwek2x.jpg', '12335', 'orange waffle battered quail eggs', 'orange waffle battered quail eggs 10pcs', 'notSale', 'Kwek Kwek'),
(13, 6, 150, 150, 'strawc4ke', 'strawberry-cake.jpg', 'asdfsad', 'Strawberry Cake', 'Strawberry Cake', 'notSale', 'Strawberry Cake'),
(14, 6, 302, 299.75, 's4nz', 'sans-rival-3-1.jpg', 'sans', 'mas masarap pa kesa sa burger machine', 'mas masarap pa kesa sa burger machine', 'notSale', 'Sans Rival'),
(15, 5, 499, 499, 'p1zz4', 'pizza.jpg', 'pizzzaaa', 'pepperoni pizza', 'pepperoni pizza', 'notSale', 'Pizza'),
(16, 5, 150, 150, 'buuurgeklasdjf', 'burger.jpg', 'asdfasdf', 'mas cheesy pa sayo', 'mas cheesy pa sayo', 'notSale', 'Cheese Burger'),
(17, 5, 70, 70, 'spaaag', 'spag.jpeg', 'safdafd', 'spaghetti na masarap', 'spaghetti na masarap', 'notSale', 'Spaghetti'),
(18, 5, 123, 123, 'chikin', 'chicken.jpg', 'asjdkf', 'hindi jollibee', 'hindi jollibee', NULL, 'Fried Chicken'),
(19, 5, 900, 900, 'samggg', 'samg.jpg', 'samggg', 'Samgyup sa bahay', 'Samgyup sa bahay', NULL, 'Samgyupsabahay'),
(20, 5, 33, 33, 'bang', 'daing.jpg', 'asdf', 'bangus galing pangasinan', 'bangus galing pangasinan', NULL, 'Daing na Bangus');

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
(1, 'aughus', 'bueno', 'aughusbueno@gmail.com', '09123456789', 'admin'),
(3, 'jules', 'diego', 'jules@gmail.com', '09123456789', 'admin'),
(4, 'raffy', 'sumbungan', 'raffytulfoinaction@gmail.com', '09123456789', 'customer'),
(6, 'abc', 'abc', 'rivera@gmail.com', '09123456789', 'customer'),
(7, '123', '123', '123@123.com', '0912 345 678', 'driver'),
(9, 'kardo', 'kardo', 'kardo@pulis.com', '0911 9119 911', 'driver');

-- --------------------------------------------------------

--
-- Stand-in structure for view `wait_driver_orders`
-- (See below for the actual view)
--
CREATE TABLE `wait_driver_orders` (
`user_id` int(11)
,`username` varchar(255)
,`order_id` int(11)
,`product_id` int(11)
,`product_img` varchar(255)
,`product_name` varchar(255)
,`address` varchar(255)
,`qty` int(11)
,`sell_price` float
,`sub_total` double
,`state` varchar(255)
);

-- --------------------------------------------------------

--
-- Structure for view `wait_driver_orders`
--
DROP TABLE IF EXISTS `wait_driver_orders`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `wait_driver_orders`  AS SELECT `user`.`id` AS `user_id`, `user`.`username` AS `username`, `co`.`order_id` AS `order_id`, `op`.`product_id` AS `product_id`, `prod`.`product_img` AS `product_img`, `prod`.`product_name` AS `product_name`, `co`.`address` AS `address`, `op`.`qty` AS `qty`, `prod`.`sell_price` AS `sell_price`, `prod`.`sell_price`* `op`.`qty` AS `sub_total`, `co`.`state` AS `state` FROM (((`order_products` `op` left join `customer_order` `co` on(`op`.`order_id` = `co`.`order_id`)) left join `product` `prod` on(`op`.`product_id` = `prod`.`product_id`)) left join `user` on(`co`.`buyer_id` = `user`.`id`)) WHERE `co`.`state` = 'wait_deliver' ;

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
  ADD PRIMARY KEY (`order_id`),
  ADD KEY `buyer_id` (`buyer_id`);

--
-- Indexes for table `driver`
--
ALTER TABLE `driver`
  ADD PRIMARY KEY (`driver_id`);

--
-- Indexes for table `feedback`
--
ALTER TABLE `feedback`
  ADD PRIMARY KEY (`feedback_id`);

--
-- Indexes for table `order_products`
--
ALTER TABLE `order_products`
  ADD KEY `fk_order_products_customer_order` (`order_id`),
  ADD KEY `fk_order_products_product` (`product_id`);

--
-- Indexes for table `product`
--
ALTER TABLE `product`
  ADD PRIMARY KEY (`product_id`),
  ADD KEY `product_category_id` (`product_category_id`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `username` (`username`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `category`
--
ALTER TABLE `category`
  MODIFY `category_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;

--
-- AUTO_INCREMENT for table `customer_order`
--
ALTER TABLE `customer_order`
  MODIFY `order_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT for table `driver`
--
ALTER TABLE `driver`
  MODIFY `driver_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT for table `feedback`
--
ALTER TABLE `feedback`
  MODIFY `feedback_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `product`
--
ALTER TABLE `product`
  MODIFY `product_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;

--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `customer_order`
--
ALTER TABLE `customer_order`
  ADD CONSTRAINT `customer_order_ibfk_1` FOREIGN KEY (`buyer_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `order_products`
--
ALTER TABLE `order_products`
  ADD CONSTRAINT `fk_order_products_customer_order` FOREIGN KEY (`order_id`) REFERENCES `customer_order` (`order_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_order_products_product` FOREIGN KEY (`product_id`) REFERENCES `product` (`product_id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `product`
--
ALTER TABLE `product`
  ADD CONSTRAINT `product_ibfk_1` FOREIGN KEY (`product_category_id`) REFERENCES `category` (`category_id`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
