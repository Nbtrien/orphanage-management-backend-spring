--
-- Cấu trúc bảng cho bảng `address`
--

CREATE TABLE `address` (
  `ADDRESS_ID` int(11) NOT NULL,
  `PROVINCE_ID` varchar(20) NOT NULL,
  `DISTRICT_ID` varchar(20) NOT NULL,
  `WARD_ID` varchar(20) NOT NULL,
  `ADDRESS_DETAIL` varchar(255) NOT NULL,
  `IS_DELETE` bool NOT NULL DEFAULT FALSE,
  `CREATE_DATE_TIME` datetime DEFAULT CURRENT_TIMESTAMP,
  `UPDATE_DATE_TIME` datetime DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `administrative_region`
--

CREATE TABLE `administrative_region` (
  `ADMINISTRATIVE_REGION_ID` int(11) NOT NULL,
  `REGION_NAME` varchar(255) NOT NULL,
  `REGION_NAME_EN` varchar(255) NOT NULL,
  `REGION_CODE_NAME` varchar(255) DEFAULT NULL,
  `REGION_CODE_NAME_EN` varchar(255) DEFAULT NULL,
  `IS_DELETE` bool NOT NULL DEFAULT FALSE,
  `CREATE_DATE_TIME` datetime DEFAULT CURRENT_TIMESTAMP,
  `UPDATE_DATE_TIME` datetime DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `administrative_unit`
--

CREATE TABLE `administrative_unit` (
  `ADMINISTRATIVE_UNIT_ID` int(11) NOT NULL,
  `UNIT_FULL_NAME` varchar(255) DEFAULT NULL,
  `UNIT_FULL_NAME_EN` varchar(255) DEFAULT NULL,
  `UNIT_SHORT_NAME` varchar(255) DEFAULT NULL,
  `UNIT_SHORT_NAME_EN` varchar(255) DEFAULT NULL,
  `UNIT_CODE_NAME` varchar(255) DEFAULT NULL,
  `UNIT_CODE_NAME_EN` varchar(255) DEFAULT NULL,
  `IS_DELETE` bool NOT NULL DEFAULT FALSE,
  `CREATE_DATE_TIME` datetime DEFAULT CURRENT_TIMESTAMP,
  `UPDATE_DATE_TIME` datetime DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `adopter`
--

CREATE TABLE `adopter` (
  `ADOPTER_ID` int(11) NOT NULL,
  `ADOPTER_FULL_NAME` varchar(255) NOT NULL,
  `ADOPTER_FIRST_NAME` varchar(255) NOT NULL,
  `ADOPTER_LAST_NAME` varchar(255) NOT NULL,
  `ADOPTER_DATE_OF_BIRTH` date NOT NULL,
  `ADOPTER_GENDER` varchar(5) NOT NULL,
  `ADOPTER_ETHNICITY` varchar(20) NOT NULL,
  `ADOPTER_NATIONALITY` varchar(20) NOT NULL,
  `ADOPTER_RELIGION` varchar(20) NOT NULL,
  `ADOPTER_MAIL_ADDRESS` varchar(255) NOT NULL,
  `ADOPTER_PHONE_NUMBER` varchar(255) NOT NULL,
  `CITIZEN_ID_NUMBER` char(12) NOT NULL,
  `SPOUSE` smallint(1) NOT NULL,
  `ADOPTION_HISTORY_ID` int(11) NOT NULL,
  `ADOPTER_ADDRESS_ID` int(11) NOT NULL,
  `MARITAL_STATUS_ID` int(11) NOT NULL,
  `IS_DELETE` bool NOT NULL DEFAULT FALSE,
  `CREATE_DATE_TIME` datetime DEFAULT CURRENT_TIMESTAMP,
  `UPDATE_DATE_TIME` datetime DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `adoption_applicant`
--

CREATE TABLE `adoption_applicant` (
  `ADOPTION_APPLICANT_ID` int(11) NOT NULL,
  `ADOPTION_APPLICANT_FULL_NAME` varchar(255) NOT NULL,
  `ADOPTION_APPLICANT_FIRST_NAME` varchar(255) NOT NULL,
  `ADOPTION_APPLICANT_LAST_NAME` varchar(255) NOT NULL,
  `ADOPTION_APPLICANT_DATE_OF_BIRTH` date NOT NULL,
  `ADOPTION_APPLICANT_GENDER` varchar(5) NOT NULL,
  `ADOPTION_APPLICANT_ETHNICITY` varchar(20) NOT NULL,
  `ADOPTION_APPLICANT_NATIONALITY` varchar(20) NOT NULL,
  `ADOPTION_APPLICANT_RELIGION` varchar(20) NOT NULL,
  `ADOPTION_APPLICANT_MAIL_ADDRESS` varchar(255) NOT NULL,
  `ADOPTION_APPLICANT_PHONE_NUMBER` varchar(255) NOT NULL,
  `CITIZEN_ID_NUMBER` char(12) NOT NULL,
  `CITIZEN_ID_IMAGE_FILE_NAME` varchar(255) NOT NULL,
  `CITIZEN_ID_IMAGE_FILE_PATH` varchar(255) NOT NULL,
  `ADOPTION_APPLICANT_ADDRESS_ID` int(11) NOT NULL,
  `MARITAL_STATUS_ID` int(11) NOT NULL,
  `ADOPTION_APPLICANT_STATUS_ID` int(11) NOT NULL,
  `PASSWORD` varchar(64) DEFAULT NULL,
  `IS_FIRST_LOGIN` tinyint(1) NOT NULL,
  `IS_SENT_NOTIFICATION_EMAIL` tinyint(1) NOT NULL,
  `IS_SENT_DETAILED_EMAIL` tinyint(1) NOT NULL,
  `IS_DELETE` bool NOT NULL DEFAULT FALSE,
  `CREATE_DATE_TIME` datetime DEFAULT CURRENT_TIMESTAMP,
  `UPDATE_DATE_TIME` datetime DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `adoption_applicant_pdf_file`
--

CREATE TABLE `adoption_applicant_pdf_file` (
  `ADOPTION_APPLICANT_PDF_FILE` int(11) NOT NULL,
  `ADOPTION_APPLICANT_ID` int(11) NOT NULL,
  `PDF_FILE_NAME` varchar(255) NOT NULL,
  `PDF_FILE_PATH` varchar(255) NOT NULL,
  `IS_DELETE` bool NOT NULL DEFAULT FALSE,
  `CREATE_DATE_TIME` datetime DEFAULT CURRENT_TIMESTAMP,
  `UPDATE_DATE_TIME` datetime DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `adoption_applicant_status`
--

CREATE TABLE `adoption_applicant_status` (
  `ADOPTION_APPLICANT_STATUS_ID` int(11) NOT NULL,
  `ADOPTION_APPLICANT_STATUS_NAME` varchar(255) NOT NULL,
  `ADOPTION_APPLICANT_STATUS_DESCRIPTION` varchar(255) NOT NULL,
  `IS_DELETE` bool NOT NULL DEFAULT FALSE,
  `CREATE_DATE_TIME` datetime DEFAULT CURRENT_TIMESTAMP,
  `UPDATE_DATE_TIME` datetime DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `adoption_history`
--

CREATE TABLE `adoption_history` (
  `ADOPTION_HISTORY_ID` int(11) NOT NULL,
  `CHILDREN_ID` int(11) NOT NULL,
  `ADOPTION_DATE` date NOT NULL,
  `ADOPTION_CERTIFICATE_FILE_NAME` varchar(255) NOT NULL,
  `ADOPTION_CERTIFICATE_FILE_PATH` varchar(255) NOT NULL,
  `IS_DELETE` bool NOT NULL DEFAULT FALSE,
  `CREATE_DATE_TIME` datetime DEFAULT CURRENT_TIMESTAMP,
  `UPDATE_DATE_TIME` datetime DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `appointment`
--

CREATE TABLE `appointment` (
  `APPOINTMENT_ID` int(11) NOT NULL,
  `ADOPTION_APPLICANT_ID` int(11) NOT NULL,
  `APPOINTMENT_STATUS` smallint(1) NOT NULL,
  `APPOINTMENT_START_DATE_TIME` datetime NOT NULL,
  `APPOINTMENT_END_DATE_TIME` datetime NOT NULL,
  `IS_DELETE` bool NOT NULL DEFAULT FALSE,
  `CREATE_DATE_TIME` datetime DEFAULT CURRENT_TIMESTAMP,
  `UPDATE_DATE_TIME` datetime DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `article`
--

CREATE TABLE `article` (
  `ARTICLE_ID` int(11) NOT NULL,
  `ARTICLE_TITLE` varchar(255) NOT NULL,
  `ARTICLE_SLUG` varchar(255) NOT NULL,
  `PUBLICATION_START_DATE_TIME` datetime NOT NULL,
  `PUBLICATION_END_DATE_TIME` datetime DEFAULT NULL,
  `ARTICLE_SUMMARY` varchar(255) NOT NULL,
  `ARTICLE_CONTENT` text DEFAULT NULL,
  `IMAGE_FILE_NAME` varchar(255) NOT NULL,
  `IMAGE_FILE_PATH` varchar(255) NOT NULL,
  `ARTICLE_CATEGORY_ID` int(11) NOT NULL,
  `IS_DELETE` bool NOT NULL DEFAULT FALSE,
  `CREATE_DATE_TIME` datetime DEFAULT CURRENT_TIMESTAMP,
  `UPDATE_DATE_TIME` datetime DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `article_category`
--

CREATE TABLE `article_category` (
  `ARTICLE_CATEGORY_ID` int(11) NOT NULL,
  `ARTICLE_CATEGORY_NAME` varchar(255) NOT NULL,
  `ARTICLE_CATEGORY_SLUG` varchar(255) NOT NULL,
  `PARENT_ID` int(11) NOT NULL,
  `IS_DELETE` bool NOT NULL DEFAULT FALSE,
  `CREATE_DATE_TIME` datetime DEFAULT CURRENT_TIMESTAMP,
  `UPDATE_DATE_TIME` datetime DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `children`
--

CREATE TABLE `children` (
  `CHILDREN_ID` int(11) NOT NULL,
  `CHILDREN_FULL_NAME` varchar(255) NOT NULL,
  `CHILDREN_FIRST_NAME` varchar(255) NOT NULL,
  `CHILDREN_LAST_NAME` varchar(255) NOT NULL,
  `CHILDREN_DATE_OF_BIRTH` date DEFAULT NULL,
  `CHILDREN_GENDER` varchar(5) NOT NULL,
  `DATE_OF_ADMITION` date NOT NULL,
  `DATE_OF_DEPARTURE` date DEFAULT NULL,
  `CHILDREN_ETHNICITY` varchar(20) NOT NULL,
  `CHILDREN_RELIGION` varchar(20) NOT NULL,
  `CHILDREN_CIRCUMSTANCE` text DEFAULT NULL,
  `IS_WAITING_ADOPTION` tinyint(1) NOT NULL,
  `CHILDREN_STATUS_ID` int(11) NOT NULL,
  `ORPHAN_TYPE_ID` int(11) NOT NULL,
  `CHILDREN_ADDRESS_ID` int(11) NOT NULL,
  `CHILDREN_FAMILY_ID` int(11) NOT NULL,
  `CHILDREN_IMAGE_ID` int(11) NOT NULL,
  `IS_DELETE` bool NOT NULL DEFAULT FALSE,
  `CREATE_DATE_TIME` datetime DEFAULT CURRENT_TIMESTAMP,
  `UPDATE_DATE_TIME` datetime DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `children_document`
--

CREATE TABLE `children_document` (
  `CHILDREN_DOCUMENT_ID` int(11) NOT NULL,
  `DOCUMENT_TYPE_ID` int(11) NOT NULL,
  `CHILDREN_ID` int(11) NOT NULL,
  `DOCUMENT_FILE_NAME` varchar(255) NOT NULL,
  `DOCUMENT_FILE_PATH` varchar(255) NOT NULL,
  `DOCUMENT_DESCRIPTION` varchar(4000) NOT NULL,
  `IS_DELETE` bool NOT NULL DEFAULT FALSE,
  `CREATE_DATE_TIME` datetime DEFAULT CURRENT_TIMESTAMP,
  `UPDATE_DATE_TIME` datetime DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `children_family_history`
--

CREATE TABLE `children_family_history` (
  `CHILDREN_FAMILY_HISTORY_ID` int(11) NOT NULL,
  `CHILDREN_ID` int(11) NOT NULL,
  `FROM_FAMILY_ID` int(11) NOT NULL,
  `TO_FAMILY_ID` int(11) NOT NULL,
  `TRANSITION_DATE` date NOT NULL,
  `TRANSITION_REASON` varchar(255) NOT NULL,
  `IS_DELETE` bool NOT NULL DEFAULT FALSE,
  `CREATE_DATE_TIME` datetime DEFAULT CURRENT_TIMESTAMP,
  `UPDATE_DATE_TIME` datetime DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `children_relative_relationship`
--

CREATE TABLE `children_relative_relationship` (
  `CHILDREN_RELATIVE_RELATIONSHIP_ID` int(11) NOT NULL,
  `CHILDREN_ID` int(11) NOT NULL,
  `RELATIVE_ID` int(11) NOT NULL,
  `IS_DELETE` bool NOT NULL DEFAULT FALSE,
  `CREATE_DATE_TIME` datetime DEFAULT CURRENT_TIMESTAMP,
  `UPDATE_DATE_TIME` datetime DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `children_status`
--

CREATE TABLE `children_status` (
  `CHILDREN_STATUS_ID` int(11) NOT NULL,
  `CHILDREN_STATUS_NAME` varchar(255) DEFAULT NULL,
  `DESCRIPTION` varchar(4000) DEFAULT NULL,
  `IS_DELETE` bool NOT NULL DEFAULT FALSE,
  `CREATE_DATE_TIME` datetime DEFAULT CURRENT_TIMESTAMP,
  `UPDATE_DATE_TIME` datetime DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `children_status_history`
--

CREATE TABLE `children_status_history` (
  `CHILDREN_STATUS_HISTORY_ID` int(11) NOT NULL,
  `CHILDREN_ID` int(11) NOT NULL,
  `CHILDREN_STATUS_ID` int(11) NOT NULL,
  `EFFECTIVE_DATE` datetime NOT NULL,
  `REASON` varchar(255) DEFAULT NULL,
  `IS_DELETE` bool NOT NULL DEFAULT FALSE,
  `CREATE_DATE_TIME` datetime DEFAULT CURRENT_TIMESTAMP,
  `UPDATE_DATE_TIME` datetime DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `district`
--

CREATE TABLE `district` (
  `DISTRICT_ID` varchar(20) NOT NULL,
  `DISTRICT_NAME` varchar(255) NOT NULL,
  `DISTRICT_NAME_EN` varchar(255) DEFAULT NULL,
  `DISTRICT_FULL_NAME` varchar(255) DEFAULT NULL,
  `DISTRICT_FULL_NAME_EN` varchar(255) DEFAULT NULL,
  `DISTRICT_CODE_NAME` varchar(255) DEFAULT NULL,
  `PROVINCE_ID` varchar(20) DEFAULT NULL,
  `ADMINISTRATIVE_UNIT_ID` int(11) DEFAULT NULL,
  `IS_DELETE` bool NOT NULL DEFAULT FALSE,
  `CREATE_DATE_TIME` datetime DEFAULT CURRENT_TIMESTAMP,
  `UPDATE_DATE_TIME` datetime DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `document_type`
--

CREATE TABLE `document_type` (
  `DOCUMENT_TYPE_ID` int(11) NOT NULL,
  `DOCUMENT_TYPE_NAME` varchar(255) NOT NULL,
  `DOCUMENT_TYPE_DESCRIPTION` varchar(4000) NOT NULL,
  `IS_DELETE` bool NOT NULL DEFAULT FALSE,
  `CREATE_DATE_TIME` datetime DEFAULT CURRENT_TIMESTAMP,
  `UPDATE_DATE_TIME` datetime DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `donation`
--

CREATE TABLE `donation` (
  `DONATION_ID` int(11) NOT NULL,
  `DONOR_ID` int(11) NOT NULL,
  `AMOUNT` double NOT NULL,
  `DONATION_TIME` datetime NOT NULL,
  `BANK_CODE` varchar(255) NOT NULL,
  `BANK_TRAN_NO` varchar(255) NOT NULL,
  `PAY_DATE` datetime NOT NULL,
  `TRANSACTION_NO` varchar(255) NOT NULL,
  `TRANSACTION_STATUS` int(2) NOT NULL,
  `DONATION_MESSAGE` text DEFAULT NULL,
  `TRANSACTION_HASH` varchar(255) DEFAULT NULL,
  `IS_USED` tinyint(1) NOT NULL,
  `IS_SENT_EMAIL` tinyint(1) NOT NULL,
  `IS_IN_BLOCKCHAIN` tinyint(1) NOT NULL,
  `IS_DELETE` bool NOT NULL DEFAULT FALSE,
  `CREATE_DATE_TIME` datetime DEFAULT CURRENT_TIMESTAMP,
  `UPDATE_DATE_TIME` datetime DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `donor`
--

CREATE TABLE `donor` (
  `DONOR_ID` int(11) NOT NULL,
  `DONOR_FULL_NAME` varchar(255) NOT NULL,
  `DONOR_FIRST_NAME` varchar(255) NOT NULL,
  `DONOR_LAST_NAME` varchar(255) NOT NULL,
  `DONOR_DATE_OF_BIRTH` date DEFAULT NULL,
  `DONOR_GENDER` varchar(5) DEFAULT NULL,
  `DONOR_MAIL_ADDRESS` varchar(255) NOT NULL,
  `DONOR_PHONE_NUMBER` varchar(255) NOT NULL,
  `DONOR_TOKEN` varchar(255) DEFAULT NULL,
  `DONOR_ADDRESS_ID` int(11) DEFAULT NULL,
  `IS_DELETE` bool NOT NULL DEFAULT FALSE,
  `CREATE_DATE_TIME` datetime DEFAULT CURRENT_TIMESTAMP,
  `UPDATE_DATE_TIME` datetime DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `employee`
--

CREATE TABLE `employee` (
  `EMPLOYEE_ID` int(11) NOT NULL,
  `EMPLOYEE_FULL_NAME` varchar(255) NOT NULL,
  `EMPLOYEE_FIRST_NAME` varchar(255) NOT NULL,
  `EMPLOYEE_LAST_NAME` varchar(255) NOT NULL,
  `EMPLOYEE_DATE_OF_BIRTH` date NOT NULL,
  `EMPLOYEE_GENDER` varchar(5) NOT NULL,
  `EMPLOYEE_PHONE_NUMBER` char(10) NOT NULL,
  `EMPLOYEE_MAIL_ADDRESS` varchar(255) DEFAULT NULL,
  `EMPLOYEE_ETHNICITY` varchar(255) NOT NULL,
  `EMPLOYEE_RELIGION` varchar(255) NOT NULL,
  `EMPLOYEE_POSITION_ID` int(11) NOT NULL,
  `EMPLOYEE_ADDRESS_ID` int(11) NOT NULL,
  `HIRE_DATE` date DEFAULT NULL,
  `END_DATE` date DEFAULT NULL,
  `IS_DELETE` bool NOT NULL DEFAULT FALSE,
  `CREATE_DATE_TIME` datetime DEFAULT CURRENT_TIMESTAMP,
  `UPDATE_DATE_TIME` datetime DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `employee_payroll`
--

CREATE TABLE `employee_payroll` (
  `EMPLOYEE_PAYROLL_ID` int(11) NOT NULL,
  `EMPLOYEE_ID` int(11) NOT NULL,
  `SALARY_ID` int(11) NOT NULL,
  `PAYROLL_AMOUNT` decimal(10,0) NOT NULL,
  `EMPLOYEE_PAYROLL_MONTH` int(11) NOT NULL,
  `EMPLOYEE_PAYROLL_YEAR` int(11) NOT NULL,
  `DATE_OF_PAYMENT` date NOT NULL,
  `PAYROLL_START_DATE` date NOT NULL,
  `PAYROLL_END_DATE` date NOT NULL,
  `IS_DELETE` bool NOT NULL DEFAULT FALSE,
  `CREATE_DATE_TIME` datetime DEFAULT CURRENT_TIMESTAMP,
  `UPDATE_DATE_TIME` datetime DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `employee_position`
--

CREATE TABLE `employee_position` (
  `EMPLOYEE_POSITION_ID` int(11) NOT NULL,
  `EMPLOYEE_POSITION_TITLE` varchar(255) NOT NULL,
  `EMPLOYEE_POSITION_DESCRIPTION` varchar(4000) NOT NULL,
  `IS_DELETE` bool NOT NULL DEFAULT FALSE,
  `CREATE_DATE_TIME` datetime DEFAULT CURRENT_TIMESTAMP,
  `UPDATE_DATE_TIME` datetime DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `family`
--

CREATE TABLE `family` (
  `FAMILY_ID` int(11) NOT NULL,
  `FAMILY_NAME` varchar(255) NOT NULL,
  `DATE_OF_FORMATION` date DEFAULT NULL,
  `DATE_OF_TERMINATION` date DEFAULT NULL,
  `FAMILY_CONDITION_ID` int(11) NOT NULL,
  `IS_DELETE` bool NOT NULL DEFAULT FALSE,
  `CREATE_DATE_TIME` datetime DEFAULT CURRENT_TIMESTAMP,
  `UPDATE_DATE_TIME` datetime DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `family_condition`
--

CREATE TABLE `family_condition` (
  `FAMILY_CONDITION_ID` int(11) NOT NULL,
  `AGE_FROM` int(2) NOT NULL,
  `AGE_TO` int(2) NOT NULL,
  `MIN_NUMBER_OF_CHILDREN` int(11) NOT NULL,
  `MAX_NUMBER_OF_CHILDREN` int(11) NOT NULL,
  `IS_DELETE` bool NOT NULL DEFAULT FALSE,
  `CREATE_DATE_TIME` datetime DEFAULT CURRENT_TIMESTAMP,
  `UPDATE_DATE_TIME` datetime DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `funding_utilization`
--

CREATE TABLE `funding_utilization` (
  `FUNDING_UTILIZATION_ID` int(11) NOT NULL,
  `DONATION_ID` int(11) NOT NULL,
  `AMOUNT` double NOT NULL,
  `FUNDING_UTILIZATION_TIME` datetime NOT NULL,
  `FAMILY_ID` int(11) NOT NULL,
  `UTILIZATION_PURPOSE` varchar(255) NOT NULL,
  `TRANSACTION_HASH` varchar(255) DEFAULT NULL,
  `IS_SENT_EMAIL` tinyint(1) NOT NULL,
  `IS_IN_BLOCKCHAIN` tinyint(1) NOT NULL,
  `IS_DELETE` bool NOT NULL DEFAULT FALSE,
  `CREATE_DATE_TIME` datetime DEFAULT CURRENT_TIMESTAMP,
  `UPDATE_DATE_TIME` datetime DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `image`
--

CREATE TABLE `image` (
  `IMAGE_ID` int(11) NOT NULL,
  `IMAGE_FILE_NAME` varchar(255) NOT NULL,
  `IMAGE_FILE_PATH` varchar(255) NOT NULL,
  `IS_DELETE` bool NOT NULL DEFAULT FALSE,
  `CREATE_DATE_TIME` datetime DEFAULT CURRENT_TIMESTAMP,
  `UPDATE_DATE_TIME` datetime DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `marital_status`
--

CREATE TABLE `marital_status` (
  `MARITAL_STATUS_ID` int(11) NOT NULL,
  `MARITAL_STATUS_NAME` varchar(255) NOT NULL,
  `MARITAL_STATUS_DESCRIPTION` varchar(255) NOT NULL,
  `IS_DELETE` bool NOT NULL DEFAULT FALSE,
  `CREATE_DATE_TIME` datetime DEFAULT CURRENT_TIMESTAMP,
  `UPDATE_DATE_TIME` datetime DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `medical_record`
--

CREATE TABLE `medical_record` (
  `MEDICAL_RECORD_ID` int(11) NOT NULL,
  `CHILDREN_ID` int(11) NOT NULL,
  `SYMPTOMS` text DEFAULT NULL,
  `RESULT` text DEFAULT NULL,
  `MEDICAL_DESCRIPTION` text DEFAULT NULL,
  `MEDICAL_DOCUMENT_FILE_NAME` varchar(255) DEFAULT NULL,
  `MEDICAL_DOCUMENT_FILE_PATH` varchar(255) DEFAULT NULL,
  `IS_DELETE` bool NOT NULL DEFAULT FALSE,
  `CREATE_DATE_TIME` datetime DEFAULT CURRENT_TIMESTAMP,
  `UPDATE_DATE_TIME` datetime DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `mother`
--

CREATE TABLE `mother` (
  `MOTHER_ID` int(11) NOT NULL,
  `MOTHER_NAME` varchar(255) NOT NULL,
  `EMPLOYEE_ID` int(11) NOT NULL,
  `FAMILY_ID` int(11) NOT NULL,
  `IS_DELETE` bool NOT NULL DEFAULT FALSE,
  `CREATE_DATE_TIME` datetime DEFAULT CURRENT_TIMESTAMP,
  `UPDATE_DATE_TIME` datetime DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `orphan_type`
--

CREATE TABLE `orphan_type` (
  `ORPHAN_TYPE_ID` int(11) NOT NULL,
  `ORPHAN_TYPE_NAME` varchar(1000) DEFAULT NULL,
  `DESCRIPTION` varchar(4000) DEFAULT NULL,
  `IS_DELETE` bool NOT NULL DEFAULT FALSE,
  `CREATE_DATE_TIME` datetime DEFAULT CURRENT_TIMESTAMP,
  `UPDATE_DATE_TIME` datetime DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `province`
--

CREATE TABLE `province` (
  `PROVINCE_ID` varchar(20) NOT NULL,
  `PROVINCE_NAME` varchar(255) NOT NULL,
  `PROVINCE_NAME_EN` varchar(255) DEFAULT NULL,
  `PROVINCE_FULL_NAME` varchar(255) NOT NULL,
  `PROVINCE_FULL_NAME_EN` varchar(255) DEFAULT NULL,
  `PROVINCE_CODE_NAME` varchar(255) DEFAULT NULL,
  `ADMINISTRATIVE_UNIT_ID` int(11) DEFAULT NULL,
  `ADMINISTRATIVE_REGION_ID` int(11) DEFAULT NULL,
  `IS_DELETE` bool NOT NULL DEFAULT FALSE,
  `CREATE_DATE_TIME` datetime DEFAULT CURRENT_TIMESTAMP,
  `UPDATE_DATE_TIME` datetime DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `relative`
--

CREATE TABLE `relative` (
  `RELATIVE_ID` int(11) NOT NULL,
  `RELATIVE_FULL_NAME` varchar(255) NOT NULL,
  `RELATIVE_FIRST_NAME` varchar(255) NOT NULL,
  `RELATIVE_LAST_NAME` varchar(255) NOT NULL,
  `RELATIVE_DATE_OF_BIRTH` date DEFAULT NULL,
  `RELATIVE_GENDER` varchar(5) NOT NULL,
  `RELATIONSHIP` varchar(255) NOT NULL,
  `RELATIVE_PHONE_NUMBER` char(10) NOT NULL,
  `RELATIVE_MAIL_ADDRESS` varchar(255) DEFAULT NULL,
  `RELATIVE_ADDRESS_ID` int(11) NOT NULL,
  `IS_DELETE` bool NOT NULL DEFAULT FALSE,
  `CREATE_DATE_TIME` datetime DEFAULT CURRENT_TIMESTAMP,
  `UPDATE_DATE_TIME` datetime DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `salary`
--

CREATE TABLE `salary` (
  `SALARY_ID` int(11) NOT NULL,
  `EMPLOYEE_POSITION_ID` int(11) NOT NULL,
  `EMPLOYEE_ID` int(11) NOT NULL,
  `SALARY_AMOUNT` decimal(10,0) NOT NULL,
  `SALARY_START_DATE` date NOT NULL,
  `SALARY_END_DATE` date NOT NULL,
  `IS_DELETE` bool NOT NULL DEFAULT FALSE,
  `CREATE_DATE_TIME` datetime DEFAULT CURRENT_TIMESTAMP,
  `UPDATE_DATE_TIME` datetime DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `token`
--

CREATE TABLE `token` (
  `TOKEN_ID` int(11) NOT NULL,
  `TOKEN` varchar(64) NOT NULL,
  `ADOPTION_APPLICANT_ID` int(10) NOT NULL,
  `ADOPTION_APPLICANT_MAIL_ADDRESS` varchar(255) DEFAULT NULL,
  `EXPIRATION_DATE_TIME_START` datetime NOT NULL,
  `EXPIRATION_DATE_TIME_END` datetime NOT NULL,
  `IS_DELETE` bool NOT NULL DEFAULT FALSE,
  `CREATE_DATE_TIME` datetime DEFAULT CURRENT_TIMESTAMP,
  `UPDATE_DATE_TIME` datetime DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `vaccination_record`
--

CREATE TABLE `vaccination_record` (
  `VACCINATION_RECORD_ID` int(11) NOT NULL,
  `CHILDREN_ID` int(11) NOT NULL,
  `VACCINE_NAME` varchar(255) NOT NULL,
  `VACCINE_TYPE` varchar(255) NOT NULL,
  `VACCINATION_DATE` date DEFAULT NULL,
  `VACCINATION_DOCUMENT_FILE_NAME` varchar(255) DEFAULT NULL,
  `VACCINATION_DOCUMENT_FILE_PATH` varchar(255) DEFAULT NULL,
  `IS_DELETE` bool NOT NULL DEFAULT FALSE,
  `CREATE_DATE_TIME` datetime DEFAULT CURRENT_TIMESTAMP,
  `UPDATE_DATE_TIME` datetime DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `ward`
--

CREATE TABLE `ward` (
  `WARD_ID` varchar(20) NOT NULL,
  `WARD_NAME` varchar(255) NOT NULL,
  `WARD_NAME_EN` varchar(255) DEFAULT NULL,
  `WARD_FULL_NAME` varchar(255) DEFAULT NULL,
  `WARD_FULL_NAME_EN` varchar(255) DEFAULT NULL,
  `WARD_CODE_NAME` varchar(255) DEFAULT NULL,
  `DISTRICT_ID` varchar(20) DEFAULT NULL,
  `ADMINISTRATIVE_UNIT_ID` int(11) DEFAULT NULL,
  `IS_DELETE` bool NOT NULL DEFAULT FALSE,
  `CREATE_DATE_TIME` datetime DEFAULT CURRENT_TIMESTAMP,
  `UPDATE_DATE_TIME` datetime DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Cấu trúc bảng cho bảng `role`
--

CREATE TABLE `role` (
  `ROLE_ID` int(11) NOT NULL,
  `ROLE_TITLE` varchar(255) NOT NULL,
  `ROLE_DESCRIPTION` varchar(255) DEFAULT NULL,
  `IS_DELETE` bool NOT NULL DEFAULT FALSE,
  `CREATE_DATE_TIME` datetime DEFAULT CURRENT_TIMESTAMP,
  `UPDATE_DATE_TIME` datetime DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `role_permission`
--

CREATE TABLE `role_permission` (
  `ROLE_PERMISSION_ID` int(11) NOT NULL,
  `ROLE_ID` int(11) NOT NULL,
  `PERMISSION_ID` int(11) NOT NULL,
  `IS_DELETE` bool NOT NULL DEFAULT FALSE,
  `CREATE_DATE_TIME` datetime DEFAULT CURRENT_TIMESTAMP,
  `UPDATE_DATE_TIME` datetime DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `user`
--

CREATE TABLE `user` (
  `USER_ID` int(11) NOT NULL,
  `USER_NAME` varchar(255) NOT NULL,
  `USER_MAIL_ADDRESS` varchar(255) NOT NULL,
  `USER_PASSWORD` varchar(255) NOT NULL,
  `IS_DELETE` bool NOT NULL DEFAULT FALSE,
  `CREATE_DATE_TIME` datetime DEFAULT CURRENT_TIMESTAMP,
  `UPDATE_DATE_TIME` datetime DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `user_role`
--

CREATE TABLE `user_role` (
  `USER_ROLE_ID` int(11) NOT NULL,
  `USER_ID` int(11) NOT NULL,
  `ROLE_ID` int(11) NOT NULL,
  `IS_DELETE` bool NOT NULL DEFAULT FALSE,
  `CREATE_DATE_TIME` datetime DEFAULT CURRENT_TIMESTAMP,
  `UPDATE_DATE_TIME` datetime DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `permission`
--

CREATE TABLE `permission` (
  `PERMISSION_ID` int(11) NOT NULL,
  `PERMISSION_TITLE` varchar(255) NOT NULL,
  `PERMISSION_DESCRIPTION` varchar(255) DEFAULT NULL,
  `IS_DELETE` bool NOT NULL DEFAULT FALSE,
  `CREATE_DATE_TIME` datetime DEFAULT CURRENT_TIMESTAMP,
  `UPDATE_DATE_TIME` datetime DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Cấu trúc bảng cho bảng `user_token`
--

CREATE TABLE `user_token` (
  `USER_TOKEN_ID` int(11) NOT NULL,
  `TOKEN` varchar(255) NOT NULL,
  `IS_EXPIRED` bool NOT NULL DEFAULT FALSE,
  `IS_REVOKED` bool NOT NULL DEFAULT FALSE,
  `USER_ID` int(11) NOT NULL,
  `IS_DELETE` bool NOT NULL DEFAULT FALSE,
  `CREATE_DATE_TIME` datetime DEFAULT CURRENT_TIMESTAMP,
  `UPDATE_DATE_TIME` datetime DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Chỉ mục cho các bảng đã đổ
--

--
-- Chỉ mục cho bảng `address`
--
ALTER TABLE `address`
  ADD PRIMARY KEY (`ADDRESS_ID`),
  ADD KEY `PROVINCE_ID` (`PROVINCE_ID`),
  ADD KEY `DISTRICT_ID` (`DISTRICT_ID`),
  ADD KEY `WARD_ID` (`WARD_ID`);

--
-- Chỉ mục cho bảng `administrative_region`
--
ALTER TABLE `administrative_region`
  ADD PRIMARY KEY (`ADMINISTRATIVE_REGION_ID`);

--
-- Chỉ mục cho bảng `administrative_unit`
--
ALTER TABLE `administrative_unit`
  ADD PRIMARY KEY (`ADMINISTRATIVE_UNIT_ID`);

--
-- Chỉ mục cho bảng `adopter`
--
ALTER TABLE `adopter`
  ADD PRIMARY KEY (`ADOPTER_ID`),
  ADD UNIQUE KEY `CITIZEN_ID_NUMBER` (`CITIZEN_ID_NUMBER`),
  ADD KEY `ADOPTION_HISTORY_ID` (`ADOPTION_HISTORY_ID`),
  ADD KEY `MARITAL_STATUS_ID` (`MARITAL_STATUS_ID`),
  ADD KEY `ADOPTER_ADDRESS_ID` (`ADOPTER_ADDRESS_ID`);

--
-- Chỉ mục cho bảng `adoption_applicant`
--
ALTER TABLE `adoption_applicant`
  ADD PRIMARY KEY (`ADOPTION_APPLICANT_ID`),
  ADD UNIQUE KEY `ADOPTION_APPLICANT_MAIL_ADDRESS` (`ADOPTION_APPLICANT_MAIL_ADDRESS`),
  ADD UNIQUE KEY `CITIZEN_ID_NUMBER` (`CITIZEN_ID_NUMBER`),
  ADD KEY `MARITAL_STATUS_ID` (`MARITAL_STATUS_ID`),
  ADD KEY `ADOPTION_APPLICANT_STATUS_ID` (`ADOPTION_APPLICANT_STATUS_ID`),
  ADD KEY `ADOPTION_APPLICANT_ADDRESS_ID` (`ADOPTION_APPLICANT_ADDRESS_ID`);

--
-- Chỉ mục cho bảng `adoption_applicant_pdf_file`
--
ALTER TABLE `adoption_applicant_pdf_file`
  ADD PRIMARY KEY (`ADOPTION_APPLICANT_PDF_FILE`),
  ADD KEY `ADOPTION_APPLICANT_ID` (`ADOPTION_APPLICANT_ID`);

--
-- Chỉ mục cho bảng `adoption_applicant_status`
--
ALTER TABLE `adoption_applicant_status`
  ADD PRIMARY KEY (`ADOPTION_APPLICANT_STATUS_ID`);

--
-- Chỉ mục cho bảng `adoption_history`
--
ALTER TABLE `adoption_history`
  ADD PRIMARY KEY (`ADOPTION_HISTORY_ID`),
  ADD KEY `CHILDREN_ID` (`CHILDREN_ID`);

--
-- Chỉ mục cho bảng `appointment`
--
ALTER TABLE `appointment`
  ADD PRIMARY KEY (`APPOINTMENT_ID`),
  ADD KEY `ADOPTION_APPLICANT_ID` (`ADOPTION_APPLICANT_ID`);

--
-- Chỉ mục cho bảng `article`
--
ALTER TABLE `article`
  ADD PRIMARY KEY (`ARTICLE_ID`),
  ADD KEY `ARTICLE_CATEGORY_ID` (`ARTICLE_CATEGORY_ID`);

--
-- Chỉ mục cho bảng `article_category`
--
ALTER TABLE `article_category`
  ADD PRIMARY KEY (`ARTICLE_CATEGORY_ID`),
  ADD KEY `PARENT_ID` (`PARENT_ID`);

--
-- Chỉ mục cho bảng `children`
--
ALTER TABLE `children`
  ADD PRIMARY KEY (`CHILDREN_ID`),
  ADD KEY `ORPHAN_TYPE_ID` (`ORPHAN_TYPE_ID`),
  ADD KEY `CHILDREN_STATUS_ID` (`CHILDREN_STATUS_ID`),
  ADD KEY `CHILDREN_ADDRESS_ID` (`CHILDREN_ADDRESS_ID`),
  ADD KEY `CHILDREN_IMAGE_ID` (`CHILDREN_IMAGE_ID`),
  ADD KEY `CHILDREN_FAMILY_ID` (`CHILDREN_FAMILY_ID`);

--
-- Chỉ mục cho bảng `children_document`
--
ALTER TABLE `children_document`
  ADD PRIMARY KEY (`CHILDREN_DOCUMENT_ID`),
  ADD KEY `DOCUMENT_TYPE_ID` (`DOCUMENT_TYPE_ID`),
  ADD KEY `CHILDREN_ID` (`CHILDREN_ID`);

--
-- Chỉ mục cho bảng `children_family_history`
--
ALTER TABLE `children_family_history`
  ADD PRIMARY KEY (`CHILDREN_FAMILY_HISTORY_ID`),
  ADD KEY `CHILDREN_ID` (`CHILDREN_ID`),
  ADD KEY `FROM_FAMILY_ID` (`FROM_FAMILY_ID`),
  ADD KEY `TO_FAMILY_ID` (`TO_FAMILY_ID`);

--
-- Chỉ mục cho bảng `children_relative_relationship`
--
ALTER TABLE `children_relative_relationship`
  ADD PRIMARY KEY (`CHILDREN_RELATIVE_RELATIONSHIP_ID`),
  ADD KEY `CHILDREN_ID` (`CHILDREN_ID`),
  ADD KEY `RELATIVE_ID` (`RELATIVE_ID`);

--
-- Chỉ mục cho bảng `children_status`
--
ALTER TABLE `children_status`
  ADD PRIMARY KEY (`CHILDREN_STATUS_ID`);

--
-- Chỉ mục cho bảng `children_status_history`
--
ALTER TABLE `children_status_history`
  ADD PRIMARY KEY (`CHILDREN_STATUS_HISTORY_ID`),
  ADD KEY `CHILDREN_ID` (`CHILDREN_ID`),
  ADD KEY `CHILDREN_STATUS_ID` (`CHILDREN_STATUS_ID`);

--
-- Chỉ mục cho bảng `district`
--
ALTER TABLE `district`
  ADD PRIMARY KEY (`DISTRICT_ID`),
  ADD KEY `ADMINISTRATIVE_UNIT_ID` (`ADMINISTRATIVE_UNIT_ID`),
  ADD KEY `PROVINCE_ID` (`PROVINCE_ID`);

--
-- Chỉ mục cho bảng `document_type`
--
ALTER TABLE `document_type`
  ADD PRIMARY KEY (`DOCUMENT_TYPE_ID`);

--
-- Chỉ mục cho bảng `donation`
--
ALTER TABLE `donation`
  ADD PRIMARY KEY (`DONATION_ID`),
  ADD KEY `DONOR_ID` (`DONOR_ID`);

--
-- Chỉ mục cho bảng `donor`
--
ALTER TABLE `donor`
  ADD PRIMARY KEY (`DONOR_ID`),
  ADD UNIQUE KEY `DONOR_TOKEN` (`DONOR_TOKEN`),
  ADD KEY `DONOR_ADDRESS_ID` (`DONOR_ADDRESS_ID`);

--
-- Chỉ mục cho bảng `employee`
--
ALTER TABLE `employee`
  ADD PRIMARY KEY (`EMPLOYEE_ID`),
  ADD UNIQUE KEY `EMPLOYEE_MAIL_ADDRESS` (`EMPLOYEE_MAIL_ADDRESS`),
  ADD KEY `EMPLOYEE_ADDRESS_ID` (`EMPLOYEE_ADDRESS_ID`),
  ADD KEY `EMPLOYEE_POSITION_ID` (`EMPLOYEE_POSITION_ID`);

--
-- Chỉ mục cho bảng `employee_payroll`
--
ALTER TABLE `employee_payroll`
  ADD PRIMARY KEY (`EMPLOYEE_PAYROLL_ID`),
  ADD KEY `EMPLOYEE_ID` (`EMPLOYEE_ID`),
  ADD KEY `SALARY_ID` (`SALARY_ID`);

--
-- Chỉ mục cho bảng `employee_position`
--
ALTER TABLE `employee_position`
  ADD PRIMARY KEY (`EMPLOYEE_POSITION_ID`);

--
-- Chỉ mục cho bảng `family`
--
ALTER TABLE `family`
  ADD PRIMARY KEY (`FAMILY_ID`),
  ADD KEY `FAMILY_CONDITION_ID` (`FAMILY_CONDITION_ID`);

--
-- Chỉ mục cho bảng `family_condition`
--
ALTER TABLE `family_condition`
  ADD PRIMARY KEY (`FAMILY_CONDITION_ID`);

--
-- Chỉ mục cho bảng `funding_utilization`
--
ALTER TABLE `funding_utilization`
  ADD PRIMARY KEY (`FUNDING_UTILIZATION_ID`),
  ADD KEY `DONATION_ID` (`DONATION_ID`),
  ADD KEY `FAMILY_ID` (`FAMILY_ID`);

--
-- Chỉ mục cho bảng `image`
--
ALTER TABLE `image`
  ADD PRIMARY KEY (`IMAGE_ID`);

--
-- Chỉ mục cho bảng `marital_status`
--
ALTER TABLE `marital_status`
  ADD PRIMARY KEY (`MARITAL_STATUS_ID`);

--
-- Chỉ mục cho bảng `medical_record`
--
ALTER TABLE `medical_record`
  ADD PRIMARY KEY (`MEDICAL_RECORD_ID`),
  ADD KEY `CHILDREN_ID` (`CHILDREN_ID`);

--
-- Chỉ mục cho bảng `mother`
--
ALTER TABLE `mother`
  ADD PRIMARY KEY (`MOTHER_ID`),
  ADD KEY `EMPLOYEE_ID` (`EMPLOYEE_ID`),
  ADD KEY `FAMILY_ID` (`FAMILY_ID`);

--
-- Chỉ mục cho bảng `orphan_type`
--
ALTER TABLE `orphan_type`
  ADD PRIMARY KEY (`ORPHAN_TYPE_ID`);

--
-- Chỉ mục cho bảng `province`
--
ALTER TABLE `province`
  ADD PRIMARY KEY (`PROVINCE_ID`),
  ADD KEY `ADMINISTRATIVE_REGION_ID` (`ADMINISTRATIVE_REGION_ID`),
  ADD KEY `ADMINISTRATIVE_UNIT_ID` (`ADMINISTRATIVE_UNIT_ID`);

--
-- Chỉ mục cho bảng `relative`
--
ALTER TABLE `relative`
  ADD PRIMARY KEY (`RELATIVE_ID`),
  ADD KEY `RELATIVE_ADDRESS_ID` (`RELATIVE_ADDRESS_ID`);

--
-- Chỉ mục cho bảng `salary`
--
ALTER TABLE `salary`
  ADD PRIMARY KEY (`SALARY_ID`),
  ADD KEY `EMPLOYEE_ID` (`EMPLOYEE_ID`),
  ADD KEY `EMPLOYEE_POSITION_ID` (`EMPLOYEE_POSITION_ID`);

--
-- Chỉ mục cho bảng `token`
--
ALTER TABLE `token`
  ADD PRIMARY KEY (`TOKEN_ID`),
  ADD UNIQUE KEY `TOKEN` (`TOKEN`),
  ADD KEY `ADOPTION_APPLICANT_ID` (`ADOPTION_APPLICANT_ID`);

--
-- Chỉ mục cho bảng `vaccination_record`
--
ALTER TABLE `vaccination_record`
  ADD PRIMARY KEY (`VACCINATION_RECORD_ID`),
  ADD KEY `CHILDREN_ID` (`CHILDREN_ID`);

--
-- Chỉ mục cho bảng `ward`
--
ALTER TABLE `ward`
  ADD PRIMARY KEY (`WARD_ID`),
  ADD KEY `ADMINISTRATIVE_UNIT_ID` (`ADMINISTRATIVE_UNIT_ID`),
  ADD KEY `DISTRICT_ID` (`DISTRICT_ID`);

--
-- Chỉ mục cho bảng `permission`
--
ALTER TABLE `permission`
  ADD PRIMARY KEY (`PERMISSION_ID`);

--
-- Chỉ mục cho bảng `role`
--
ALTER TABLE `role`
  ADD PRIMARY KEY (`ROLE_ID`);

--
-- Chỉ mục cho bảng `role_permission`
--
ALTER TABLE `role_permission`
  ADD PRIMARY KEY (`ROLE_PERMISSION_ID`),
  ADD KEY `ROLE_ID` (`ROLE_ID`),
  ADD KEY `PERMISSION_ID` (`PERMISSION_ID`);

--
-- Chỉ mục cho bảng `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`USER_ID`);

--
-- Chỉ mục cho bảng `user_role`
--
ALTER TABLE `user_role`
  ADD PRIMARY KEY (`USER_ROLE_ID`),
  ADD KEY `USER_ID` (`USER_ID`),
  ADD KEY `ROLE_ID` (`ROLE_ID`);

--
-- Chỉ mục cho bảng `user_token`
--
ALTER TABLE `user_token`
  ADD PRIMARY KEY (`USER_TOKEN_ID`),
  ADD KEY `USER_ID` (`USER_ID`);

--
-- AUTO_INCREMENT cho các bảng đã đổ
--

--
-- AUTO_INCREMENT cho bảng `address`
--
ALTER TABLE `address`
  MODIFY `ADDRESS_ID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `administrative_region`
--
ALTER TABLE `administrative_region`
  MODIFY `ADMINISTRATIVE_REGION_ID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `administrative_unit`
--
ALTER TABLE `administrative_unit`
  MODIFY `ADMINISTRATIVE_UNIT_ID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `adopter`
--
ALTER TABLE `adopter`
  MODIFY `ADOPTER_ID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `adoption_applicant`
--
ALTER TABLE `adoption_applicant`
  MODIFY `ADOPTION_APPLICANT_ID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `adoption_applicant_pdf_file`
--
ALTER TABLE `adoption_applicant_pdf_file`
  MODIFY `ADOPTION_APPLICANT_PDF_FILE` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `adoption_applicant_status`
--
ALTER TABLE `adoption_applicant_status`
  MODIFY `ADOPTION_APPLICANT_STATUS_ID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `adoption_history`
--
ALTER TABLE `adoption_history`
  MODIFY `ADOPTION_HISTORY_ID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `appointment`
--
ALTER TABLE `appointment`
  MODIFY `APPOINTMENT_ID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `article`
--
ALTER TABLE `article`
  MODIFY `ARTICLE_ID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `article_category`
--
ALTER TABLE `article_category`
  MODIFY `ARTICLE_CATEGORY_ID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `children`
--
ALTER TABLE `children`
  MODIFY `CHILDREN_ID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `children_document`
--
ALTER TABLE `children_document`
  MODIFY `CHILDREN_DOCUMENT_ID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `children_family_history`
--
ALTER TABLE `children_family_history`
  MODIFY `CHILDREN_FAMILY_HISTORY_ID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `children_relative_relationship`
--
ALTER TABLE `children_relative_relationship`
  MODIFY `CHILDREN_RELATIVE_RELATIONSHIP_ID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `children_status`
--
ALTER TABLE `children_status`
  MODIFY `CHILDREN_STATUS_ID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `children_status_history`
--
ALTER TABLE `children_status_history`
  MODIFY `CHILDREN_STATUS_HISTORY_ID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `document_type`
--
ALTER TABLE `document_type`
  MODIFY `DOCUMENT_TYPE_ID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `donation`
--
ALTER TABLE `donation`
  MODIFY `DONATION_ID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `donor`
--
ALTER TABLE `donor`
  MODIFY `DONOR_ID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `employee`
--
ALTER TABLE `employee`
  MODIFY `EMPLOYEE_ID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `employee_payroll`
--
ALTER TABLE `employee_payroll`
  MODIFY `EMPLOYEE_PAYROLL_ID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `employee_position`
--
ALTER TABLE `employee_position`
  MODIFY `EMPLOYEE_POSITION_ID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `family`
--
ALTER TABLE `family`
  MODIFY `FAMILY_ID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `family_condition`
--
ALTER TABLE `family_condition`
  MODIFY `FAMILY_CONDITION_ID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `funding_utilization`
--
ALTER TABLE `funding_utilization`
  MODIFY `FUNDING_UTILIZATION_ID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `image`
--
ALTER TABLE `image`
  MODIFY `IMAGE_ID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `marital_status`
--
ALTER TABLE `marital_status`
  MODIFY `MARITAL_STATUS_ID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `medical_record`
--
ALTER TABLE `medical_record`
  MODIFY `MEDICAL_RECORD_ID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `mother`
--
ALTER TABLE `mother`
  MODIFY `MOTHER_ID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `orphan_type`
--
ALTER TABLE `orphan_type`
  MODIFY `ORPHAN_TYPE_ID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `relative`
--
ALTER TABLE `relative`
  MODIFY `RELATIVE_ID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `salary`
--
ALTER TABLE `salary`
  MODIFY `SALARY_ID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `token`
--
ALTER TABLE `token`
  MODIFY `TOKEN_ID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `vaccination_record`
--
ALTER TABLE `vaccination_record`
  MODIFY `VACCINATION_RECORD_ID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `permission`
--
ALTER TABLE `permission`
  MODIFY `PERMISSION_ID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `role`
--
ALTER TABLE `role`
  MODIFY `ROLE_ID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `role_permission`
--
ALTER TABLE `role_permission`
  MODIFY `ROLE_PERMISSION_ID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `user`
--
ALTER TABLE `user`
  MODIFY `USER_ID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `user_role`
--
ALTER TABLE `user_role`
  MODIFY `USER_ROLE_ID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `user_token`
--
ALTER TABLE `user_token`
  MODIFY `USER_TOKEN_ID` int(11) NOT NULL AUTO_INCREMENT;