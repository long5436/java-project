-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Dec 26, 2022 at 11:34 AM
-- Server version: 5.7.39
-- PHP Version: 7.4.33

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `doandb`
--

-- --------------------------------------------------------

--
-- Table structure for table `tbl_category`
--

CREATE TABLE `tbl_category` (
  `category_id` varchar(10) NOT NULL,
  `category_name` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `tbl_category`
--

INSERT INTO `tbl_category` (`category_id`, `category_name`) VALUES
('BP01', 'Ban phim co'),
('DT01', 'Dien thoai'),
('NC01', 'Noi com dien'),
('TL01', 'Tu lanh'),
('TV01', 'Tivi');

-- --------------------------------------------------------

--
-- Table structure for table `tbl_product`
--

CREATE TABLE `tbl_product` (
  `product_id` varchar(11) NOT NULL,
  `category_id` varchar(11) NOT NULL,
  `product_name` varchar(50) NOT NULL,
  `description` varchar(200) NOT NULL,
  `price` double NOT NULL,
  `image_name` varchar(200) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `tbl_product`
--

INSERT INTO `tbl_product` (`product_id`, `category_id`, `product_name`, `description`, `price`, `image_name`) VALUES
('SP01', 'DT01', 'Iphone 14 pro max', 'ip 14 moi nhat', 32490000, '1672041824510.jpg'),
('SP03', 'NC01', 'Nồi cơm điện nắp gài Delites', 'Nồi cơm điện Delites với mẫu mã trẻ trung rất phù hợp nhiều sở thích người dùng', 385000, '1672053767064.jpg'),
('SP05', 'DT01', 'Samsung Galaxy S22 Bora Purple', 'Samsung Galaxy S22 màu tím (Bora Purple)', 12990000, '1672053613833.jpg'),
('SP06', 'TV01', 'Smart Tivi Samsung 4K Crystal UHD', 'Smart Tivi Samsung 4K Crystal UHD 43 inch UA43AU7200', 10050000, '1672054069681.jpg');

-- --------------------------------------------------------

--
-- Table structure for table `tbl_user`
--

CREATE TABLE `tbl_user` (
  `username` varchar(50) NOT NULL,
  `password` varchar(200) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `tbl_user`
--

INSERT INTO `tbl_user` (`username`, `password`) VALUES
('admin', '$5/SV2q4S1GvbNvICANEPNmMl2g2OWUCHnwznzRPH+7g$vPJbNadQh7vGOKu/SdRDK/iRuwlgGMz582yD7StQh/w8EdwlEebTlajX15P9P3z0+whn/XSQFqeyXbpESUQKbQ');

-- --------------------------------------------------------

--
-- Table structure for table `tbl_warehouse`
--

CREATE TABLE `tbl_warehouse` (
  `product_id` varchar(11) NOT NULL,
  `quantity` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `tbl_warehouse`
--

INSERT INTO `tbl_warehouse` (`product_id`, `quantity`) VALUES
('SP01', 12),
('SP03', 0),
('SP05', 1),
('SP06', 14);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `tbl_category`
--
ALTER TABLE `tbl_category`
  ADD PRIMARY KEY (`category_id`);

--
-- Indexes for table `tbl_product`
--
ALTER TABLE `tbl_product`
  ADD PRIMARY KEY (`product_id`);

--
-- Indexes for table `tbl_user`
--
ALTER TABLE `tbl_user`
  ADD PRIMARY KEY (`username`);

--
-- Indexes for table `tbl_warehouse`
--
ALTER TABLE `tbl_warehouse`
  ADD PRIMARY KEY (`product_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
