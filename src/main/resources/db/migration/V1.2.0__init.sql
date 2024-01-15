-- --
-- -- Chỉ mục cho các bảng đã đổ
-- --

-- --
-- -- Chỉ mục cho bảng `address`
-- --
-- ALTER TABLE `address`
--   ADD PRIMARY KEY (`ADDRESS_ID`),
--   ADD KEY `PROVINCE_ID` (`PROVINCE_ID`),
--   ADD KEY `DISTRICT_ID` (`DISTRICT_ID`),
--   ADD KEY `WARD_ID` (`WARD_ID`);

-- --
-- -- Chỉ mục cho bảng `administrative_region`
-- --
-- ALTER TABLE `administrative_region`
--   ADD PRIMARY KEY (`ADMINISTRATIVE_REGION_ID`);

-- --
-- -- Chỉ mục cho bảng `administrative_unit`
-- --
-- ALTER TABLE `administrative_unit`
--   ADD PRIMARY KEY (`ADMINISTRATIVE_UNIT_ID`);

-- --
-- -- Chỉ mục cho bảng `adopter`
-- --
-- ALTER TABLE `adopter`
--   ADD PRIMARY KEY (`ADOPTER_ID`),
--   ADD UNIQUE KEY `CITIZEN_ID_NUMBER` (`CITIZEN_ID_NUMBER`),
--   ADD KEY `ADOPTION_HISTORY_ID` (`ADOPTION_HISTORY_ID`),
--   ADD KEY `MARITAL_STATUS_ID` (`MARITAL_STATUS_ID`),
--   ADD KEY `ADOPTER_ADDRESS_ID` (`ADOPTER_ADDRESS_ID`);

-- --
-- -- Chỉ mục cho bảng `adoption_applicant`
-- --
-- ALTER TABLE `adoption_applicant`
--   ADD PRIMARY KEY (`ADOPTION_APPLICANT_ID`),
--   ADD UNIQUE KEY `ADOPTION_APPLICANT_MAIL_ADDRESS` (`ADOPTION_APPLICANT_MAIL_ADDRESS`),
--   ADD UNIQUE KEY `CITIZEN_ID_NUMBER` (`CITIZEN_ID_NUMBER`),
--   ADD KEY `MARITAL_STATUS_ID` (`MARITAL_STATUS_ID`),
--   ADD KEY `ADOPTION_APPLICANT_STATUS_ID` (`ADOPTION_APPLICANT_STATUS_ID`),
--   ADD KEY `ADOPTION_APPLICANT_ADDRESS_ID` (`ADOPTION_APPLICANT_ADDRESS_ID`);

-- --
-- -- Chỉ mục cho bảng `adoption_applicant_pdf_file`
-- --
-- ALTER TABLE `adoption_applicant_pdf_file`
--   ADD PRIMARY KEY (`ADOPTION_APPLICANT_PDF_FILE`),
--   ADD KEY `ADOPTION_APPLICANT_ID` (`ADOPTION_APPLICANT_ID`);

-- --
-- -- Chỉ mục cho bảng `adoption_applicant_status`
-- --
-- ALTER TABLE `adoption_applicant_status`
--   ADD PRIMARY KEY (`ADOPTION_APPLICANT_STATUS_ID`);

-- --
-- -- Chỉ mục cho bảng `adoption_history`
-- --
-- ALTER TABLE `adoption_history`
--   ADD PRIMARY KEY (`ADOPTION_HISTORY_ID`),
--   ADD KEY `CHILDREN_ID` (`CHILDREN_ID`);

-- --
-- -- Chỉ mục cho bảng `appointment`
-- --
-- ALTER TABLE `appointment`
--   ADD PRIMARY KEY (`APPOINTMENT_ID`),
--   ADD KEY `ADOPTION_APPLICANT_ID` (`ADOPTION_APPLICANT_ID`);

-- --
-- -- Chỉ mục cho bảng `article`
-- --
-- ALTER TABLE `article`
--   ADD PRIMARY KEY (`ARTICLE_ID`),
--   ADD KEY `ARTICLE_CATEGORY_ID` (`ARTICLE_CATEGORY_ID`);

-- --
-- -- Chỉ mục cho bảng `article_category`
-- --
-- ALTER TABLE `article_category`
--   ADD PRIMARY KEY (`ARTICLE_CATEGORY_ID`),
--   ADD KEY `PARENT_ID` (`PARENT_ID`);

-- --
-- -- Chỉ mục cho bảng `children`
-- --
-- ALTER TABLE `children`
--   ADD PRIMARY KEY (`CHILDREN_ID`),
--   ADD KEY `ORPHAN_TYPE_ID` (`ORPHAN_TYPE_ID`),
--   ADD KEY `CHILDREN_STATUS_ID` (`CHILDREN_STATUS_ID`),
--   ADD KEY `CHILDREN_ADDRESS_ID` (`CHILDREN_ADDRESS_ID`),
--   ADD KEY `CHILDREN_IMAGE_ID` (`CHILDREN_IMAGE_ID`),
--   ADD KEY `CHILDREN_FAMILY_ID` (`CHILDREN_FAMILY_ID`);

-- --
-- -- Chỉ mục cho bảng `children_document`
-- --
-- ALTER TABLE `children_document`
--   ADD PRIMARY KEY (`CHILDREN_DOCUMENT_ID`),
--   ADD KEY `DOCUMENT_TYPE_ID` (`DOCUMENT_TYPE_ID`),
--   ADD KEY `CHILDREN_ID` (`CHILDREN_ID`);

-- --
-- -- Chỉ mục cho bảng `children_family_history`
-- --
-- ALTER TABLE `children_family_history`
--   ADD PRIMARY KEY (`CHILDREN_FAMILY_HISTORY_ID`),
--   ADD KEY `CHILDREN_ID` (`CHILDREN_ID`),
--   ADD KEY `FROM_FAMILY_ID` (`FROM_FAMILY_ID`),
--   ADD KEY `TO_FAMILY_ID` (`TO_FAMILY_ID`);

-- --
-- -- Chỉ mục cho bảng `children_relative_relationship`
-- --
-- ALTER TABLE `children_relative_relationship`
--   ADD PRIMARY KEY (`CHILDREN_RELATIVE_RELATIONSHIP_ID`),
--   ADD KEY `CHILDREN_ID` (`CHILDREN_ID`),
--   ADD KEY `RELATIVE_ID` (`RELATIVE_ID`);

-- --
-- -- Chỉ mục cho bảng `children_status`
-- --
-- ALTER TABLE `children_status`
--   ADD PRIMARY KEY (`CHILDREN_STATUS_ID`);

-- --
-- -- Chỉ mục cho bảng `children_status_history`
-- --
-- ALTER TABLE `children_status_history`
--   ADD PRIMARY KEY (`CHILDREN_STATUS_HISTORY_ID`),
--   ADD KEY `CHILDREN_ID` (`CHILDREN_ID`),
--   ADD KEY `CHILDREN_STATUS_ID` (`CHILDREN_STATUS_ID`);

-- --
-- -- Chỉ mục cho bảng `district`
-- --
-- ALTER TABLE `district`
--   ADD PRIMARY KEY (`DISTRICT_ID`),
--   ADD KEY `ADMINISTRATIVE_UNIT_ID` (`ADMINISTRATIVE_UNIT_ID`),
--   ADD KEY `PROVINCE_ID` (`PROVINCE_ID`);

-- --
-- -- Chỉ mục cho bảng `document_type`
-- --
-- ALTER TABLE `document_type`
--   ADD PRIMARY KEY (`DOCUMENT_TYPE_ID`);

-- --
-- -- Chỉ mục cho bảng `donation`
-- --
-- ALTER TABLE `donation`
--   ADD PRIMARY KEY (`DONATION_ID`),
--   ADD KEY `DONOR_ID` (`DONOR_ID`);

-- --
-- -- Chỉ mục cho bảng `donor`
-- --
-- ALTER TABLE `donor`
--   ADD PRIMARY KEY (`DONOR_ID`),
--   ADD UNIQUE KEY `DONOR_TOKEN` (`DONOR_TOKEN`),
--   ADD KEY `DONOR_ADDRESS_ID` (`DONOR_ADDRESS_ID`);

-- --
-- -- Chỉ mục cho bảng `employee`
-- --
-- ALTER TABLE `employee`
--   ADD PRIMARY KEY (`EMPLOYEE_ID`),
--   ADD UNIQUE KEY `EMPLOYEE_MAIL_ADDRESS` (`EMPLOYEE_MAIL_ADDRESS`),
--   ADD KEY `EMPLOYEE_ADDRESS_ID` (`EMPLOYEE_ADDRESS_ID`),
--   ADD KEY `EMPLOYEE_POSITION_ID` (`EMPLOYEE_POSITION_ID`);

-- --
-- -- Chỉ mục cho bảng `employee_payroll`
-- --
-- ALTER TABLE `employee_payroll`
--   ADD PRIMARY KEY (`EMPLOYEE_PAYROLL_ID`),
--   ADD KEY `EMPLOYEE_ID` (`EMPLOYEE_ID`),
--   ADD KEY `SALARY_ID` (`SALARY_ID`);

-- --
-- -- Chỉ mục cho bảng `employee_position`
-- --
-- ALTER TABLE `employee_position`
--   ADD PRIMARY KEY (`EMPLOYEE_POSITION_ID`);

-- --
-- -- Chỉ mục cho bảng `family`
-- --
-- ALTER TABLE `family`
--   ADD PRIMARY KEY (`FAMILY_ID`),
--   ADD KEY `FAMILY_CONDITION_ID` (`FAMILY_CONDITION_ID`);

-- --
-- -- Chỉ mục cho bảng `family_condition`
-- --
-- ALTER TABLE `family_condition`
--   ADD PRIMARY KEY (`FAMILY_CONDITION_ID`);

-- --
-- -- Chỉ mục cho bảng `funding_utilization`
-- --
-- ALTER TABLE `funding_utilization`
--   ADD PRIMARY KEY (`FUNDING_UTILIZATION_ID`),
--   ADD KEY `DONATION_ID` (`DONATION_ID`),
--   ADD KEY `FAMILY_ID` (`FAMILY_ID`);

-- --
-- -- Chỉ mục cho bảng `image`
-- --
-- ALTER TABLE `image`
--   ADD PRIMARY KEY (`IMAGE_ID`);

-- --
-- -- Chỉ mục cho bảng `marital_status`
-- --
-- ALTER TABLE `marital_status`
--   ADD PRIMARY KEY (`MARITAL_STATUS_ID`);

-- --
-- -- Chỉ mục cho bảng `medical_record`
-- --
-- ALTER TABLE `medical_record`
--   ADD PRIMARY KEY (`MEDICAL_RECORD_ID`),
--   ADD KEY `CHILDREN_ID` (`CHILDREN_ID`);

-- --
-- -- Chỉ mục cho bảng `mother`
-- --
-- ALTER TABLE `mother`
--   ADD PRIMARY KEY (`MOTHER_ID`),
--   ADD KEY `EMPLOYEE_ID` (`EMPLOYEE_ID`),
--   ADD KEY `FAMILY_ID` (`FAMILY_ID`);

-- --
-- -- Chỉ mục cho bảng `orphan_type`
-- --
-- ALTER TABLE `orphan_type`
--   ADD PRIMARY KEY (`ORPHAN_TYPE_ID`);

-- --
-- -- Chỉ mục cho bảng `province`
-- --
-- ALTER TABLE `province`
--   ADD PRIMARY KEY (`PROVINCE_ID`),
--   ADD KEY `ADMINISTRATIVE_REGION_ID` (`ADMINISTRATIVE_REGION_ID`),
--   ADD KEY `ADMINISTRATIVE_UNIT_ID` (`ADMINISTRATIVE_UNIT_ID`);

-- --
-- -- Chỉ mục cho bảng `relative`
-- --
-- ALTER TABLE `relative`
--   ADD PRIMARY KEY (`RELATIVE_ID`),
--   ADD KEY `RELATIVE_ADDRESS_ID` (`RELATIVE_ADDRESS_ID`);

-- --
-- -- Chỉ mục cho bảng `salary`
-- --
-- ALTER TABLE `salary`
--   ADD PRIMARY KEY (`SALARY_ID`),
--   ADD KEY `EMPLOYEE_ID` (`EMPLOYEE_ID`),
--   ADD KEY `EMPLOYEE_POSITION_ID` (`EMPLOYEE_POSITION_ID`);

-- --
-- -- Chỉ mục cho bảng `token`
-- --
-- ALTER TABLE `token`
--   ADD PRIMARY KEY (`TOKEN_ID`),
--   ADD UNIQUE KEY `TOKEN` (`TOKEN`),
--   ADD KEY `ADOPTION_APPLICANT_ID` (`ADOPTION_APPLICANT_ID`);

-- --
-- -- Chỉ mục cho bảng `vaccination_record`
-- --
-- ALTER TABLE `vaccination_record`
--   ADD PRIMARY KEY (`VACCINATION_RECORD_ID`),
--   ADD KEY `CHILDREN_ID` (`CHILDREN_ID`);

-- --
-- -- Chỉ mục cho bảng `ward`
-- --
-- ALTER TABLE `ward`
--   ADD PRIMARY KEY (`WARD_ID`),
--   ADD KEY `ADMINISTRATIVE_UNIT_ID` (`ADMINISTRATIVE_UNIT_ID`),
--   ADD KEY `DISTRICT_ID` (`DISTRICT_ID`);

-- --
-- -- Chỉ mục cho bảng `permission`
-- --
-- ALTER TABLE `permission`
--   ADD PRIMARY KEY (`PERMISSION_ID`);

-- --
-- -- Chỉ mục cho bảng `role`
-- --
-- ALTER TABLE `role`
--   ADD PRIMARY KEY (`ROLE_ID`);

-- --
-- -- Chỉ mục cho bảng `role_permission`
-- --
-- ALTER TABLE `role_permission`
--   ADD PRIMARY KEY (`ROLE_PERMISSION_ID`),
--   ADD KEY `ROLE_ID` (`ROLE_ID`),
--   ADD KEY `PERMISSION_ID` (`PERMISSION_ID`);

-- --
-- -- Chỉ mục cho bảng `user`
-- --
-- ALTER TABLE `user`
--   ADD PRIMARY KEY (`USER_ID`);

-- --
-- -- Chỉ mục cho bảng `user_role`
-- --
-- ALTER TABLE `user_role`
--   ADD PRIMARY KEY (`USER_ROLE_ID`),
--   ADD KEY `USER_ID` (`USER_ID`),
--   ADD KEY `ROLE_ID` (`ROLE_ID`);

-- --
-- -- AUTO_INCREMENT cho các bảng đã đổ
-- --

-- --
-- -- AUTO_INCREMENT cho bảng `address`
-- --
-- ALTER TABLE `address`
--   MODIFY `ADDRESS_ID` int(11) NOT NULL AUTO_INCREMENT;

-- --
-- -- AUTO_INCREMENT cho bảng `administrative_region`
-- --
-- ALTER TABLE `administrative_region`
--   MODIFY `ADMINISTRATIVE_REGION_ID` int(11) NOT NULL AUTO_INCREMENT;

-- --
-- -- AUTO_INCREMENT cho bảng `administrative_unit`
-- --
-- ALTER TABLE `administrative_unit`
--   MODIFY `ADMINISTRATIVE_UNIT_ID` int(11) NOT NULL AUTO_INCREMENT;

-- --
-- -- AUTO_INCREMENT cho bảng `adopter`
-- --
-- ALTER TABLE `adopter`
--   MODIFY `ADOPTER_ID` int(11) NOT NULL AUTO_INCREMENT;

-- --
-- -- AUTO_INCREMENT cho bảng `adoption_applicant`
-- --
-- ALTER TABLE `adoption_applicant`
--   MODIFY `ADOPTION_APPLICANT_ID` int(11) NOT NULL AUTO_INCREMENT;

-- --
-- -- AUTO_INCREMENT cho bảng `adoption_applicant_pdf_file`
-- --
-- ALTER TABLE `adoption_applicant_pdf_file`
--   MODIFY `ADOPTION_APPLICANT_PDF_FILE` int(11) NOT NULL AUTO_INCREMENT;

-- --
-- -- AUTO_INCREMENT cho bảng `adoption_applicant_status`
-- --
-- ALTER TABLE `adoption_applicant_status`
--   MODIFY `ADOPTION_APPLICANT_STATUS_ID` int(11) NOT NULL AUTO_INCREMENT;

-- --
-- -- AUTO_INCREMENT cho bảng `adoption_history`
-- --
-- ALTER TABLE `adoption_history`
--   MODIFY `ADOPTION_HISTORY_ID` int(11) NOT NULL AUTO_INCREMENT;

-- --
-- -- AUTO_INCREMENT cho bảng `appointment`
-- --
-- ALTER TABLE `appointment`
--   MODIFY `APPOINTMENT_ID` int(11) NOT NULL AUTO_INCREMENT;

-- --
-- -- AUTO_INCREMENT cho bảng `article`
-- --
-- ALTER TABLE `article`
--   MODIFY `ARTICLE_ID` int(11) NOT NULL AUTO_INCREMENT;

-- --
-- -- AUTO_INCREMENT cho bảng `article_category`
-- --
-- ALTER TABLE `article_category`
--   MODIFY `ARTICLE_CATEGORY_ID` int(11) NOT NULL AUTO_INCREMENT;

-- --
-- -- AUTO_INCREMENT cho bảng `children`
-- --
-- ALTER TABLE `children`
--   MODIFY `CHILDREN_ID` int(11) NOT NULL AUTO_INCREMENT;

-- --
-- -- AUTO_INCREMENT cho bảng `children_document`
-- --
-- ALTER TABLE `children_document`
--   MODIFY `CHILDREN_DOCUMENT_ID` int(11) NOT NULL AUTO_INCREMENT;

-- --
-- -- AUTO_INCREMENT cho bảng `children_family_history`
-- --
-- ALTER TABLE `children_family_history`
--   MODIFY `CHILDREN_FAMILY_HISTORY_ID` int(11) NOT NULL AUTO_INCREMENT;

-- --
-- -- AUTO_INCREMENT cho bảng `children_relative_relationship`
-- --
-- ALTER TABLE `children_relative_relationship`
--   MODIFY `CHILDREN_RELATIVE_RELATIONSHIP_ID` int(11) NOT NULL AUTO_INCREMENT;

-- --
-- -- AUTO_INCREMENT cho bảng `children_status`
-- --
-- ALTER TABLE `children_status`
--   MODIFY `CHILDREN_STATUS_ID` int(11) NOT NULL AUTO_INCREMENT;

-- --
-- -- AUTO_INCREMENT cho bảng `children_status_history`
-- --
-- ALTER TABLE `children_status_history`
--   MODIFY `CHILDREN_STATUS_HISTORY_ID` int(11) NOT NULL AUTO_INCREMENT;

-- --
-- -- AUTO_INCREMENT cho bảng `document_type`
-- --
-- ALTER TABLE `document_type`
--   MODIFY `DOCUMENT_TYPE_ID` int(11) NOT NULL AUTO_INCREMENT;

-- --
-- -- AUTO_INCREMENT cho bảng `donation`
-- --
-- ALTER TABLE `donation`
--   MODIFY `DONATION_ID` int(11) NOT NULL AUTO_INCREMENT;

-- --
-- -- AUTO_INCREMENT cho bảng `donor`
-- --
-- ALTER TABLE `donor`
--   MODIFY `DONOR_ID` int(11) NOT NULL AUTO_INCREMENT;

-- --
-- -- AUTO_INCREMENT cho bảng `employee`
-- --
-- ALTER TABLE `employee`
--   MODIFY `EMPLOYEE_ID` int(11) NOT NULL AUTO_INCREMENT;

-- --
-- -- AUTO_INCREMENT cho bảng `employee_payroll`
-- --
-- ALTER TABLE `employee_payroll`
--   MODIFY `EMPLOYEE_PAYROLL_ID` int(11) NOT NULL AUTO_INCREMENT;

-- --
-- -- AUTO_INCREMENT cho bảng `employee_position`
-- --
-- ALTER TABLE `employee_position`
--   MODIFY `EMPLOYEE_POSITION_ID` int(11) NOT NULL AUTO_INCREMENT;

-- --
-- -- AUTO_INCREMENT cho bảng `family`
-- --
-- ALTER TABLE `family`
--   MODIFY `FAMILY_ID` int(11) NOT NULL AUTO_INCREMENT;

-- --
-- -- AUTO_INCREMENT cho bảng `family_condition`
-- --
-- ALTER TABLE `family_condition`
--   MODIFY `FAMILY_CONDITION_ID` int(11) NOT NULL AUTO_INCREMENT;

-- --
-- -- AUTO_INCREMENT cho bảng `funding_utilization`
-- --
-- ALTER TABLE `funding_utilization`
--   MODIFY `FUNDING_UTILIZATION_ID` int(11) NOT NULL AUTO_INCREMENT;

-- --
-- -- AUTO_INCREMENT cho bảng `image`
-- --
-- ALTER TABLE `image`
--   MODIFY `IMAGE_ID` int(11) NOT NULL AUTO_INCREMENT;

-- --
-- -- AUTO_INCREMENT cho bảng `marital_status`
-- --
-- ALTER TABLE `marital_status`
--   MODIFY `MARITAL_STATUS_ID` int(11) NOT NULL AUTO_INCREMENT;

-- --
-- -- AUTO_INCREMENT cho bảng `medical_record`
-- --
-- ALTER TABLE `medical_record`
--   MODIFY `MEDICAL_RECORD_ID` int(11) NOT NULL AUTO_INCREMENT;

-- --
-- -- AUTO_INCREMENT cho bảng `mother`
-- --
-- ALTER TABLE `mother`
--   MODIFY `MOTHER_ID` int(11) NOT NULL AUTO_INCREMENT;

-- --
-- -- AUTO_INCREMENT cho bảng `orphan_type`
-- --
-- ALTER TABLE `orphan_type`
--   MODIFY `ORPHAN_TYPE_ID` int(11) NOT NULL AUTO_INCREMENT;

-- --
-- -- AUTO_INCREMENT cho bảng `relative`
-- --
-- ALTER TABLE `relative`
--   MODIFY `RELATIVE_ID` int(11) NOT NULL AUTO_INCREMENT;

-- --
-- -- AUTO_INCREMENT cho bảng `salary`
-- --
-- ALTER TABLE `salary`
--   MODIFY `SALARY_ID` int(11) NOT NULL AUTO_INCREMENT;

-- --
-- -- AUTO_INCREMENT cho bảng `token`
-- --
-- ALTER TABLE `token`
--   MODIFY `TOKEN_ID` int(11) NOT NULL AUTO_INCREMENT;

-- --
-- -- AUTO_INCREMENT cho bảng `vaccination_record`
-- --
-- ALTER TABLE `vaccination_record`
--   MODIFY `VACCINATION_RECORD_ID` int(11) NOT NULL AUTO_INCREMENT;

-- --
-- -- AUTO_INCREMENT cho bảng `permission`
-- --
-- ALTER TABLE `permission`
--   MODIFY `PERMISSION_ID` int(11) NOT NULL AUTO_INCREMENT;

-- --
-- -- AUTO_INCREMENT cho bảng `role`
-- --
-- ALTER TABLE `role`
--   MODIFY `ROLE_ID` int(11) NOT NULL AUTO_INCREMENT;

-- --
-- -- AUTO_INCREMENT cho bảng `role_permission`
-- --
-- ALTER TABLE `role_permission`
--   MODIFY `ROLE_PERMISSION_ID` int(11) NOT NULL AUTO_INCREMENT;

-- --
-- -- AUTO_INCREMENT cho bảng `user`
-- --
-- ALTER TABLE `user`
--   MODIFY `USER_ID` int(11) NOT NULL AUTO_INCREMENT;

-- --
-- -- AUTO_INCREMENT cho bảng `user_role`
-- --
-- ALTER TABLE `user_role`
--   MODIFY `USER_ROLE_ID` int(11) NOT NULL AUTO_INCREMENT;

--
-- Các ràng buộc cho các bảng đã đổ
--

--
-- Các ràng buộc cho bảng `address`
--
ALTER TABLE `address`
  ADD CONSTRAINT `address_ibfk_1` FOREIGN KEY (`PROVINCE_ID`) REFERENCES `province` (`PROVINCE_ID`),
  ADD CONSTRAINT `address_ibfk_2` FOREIGN KEY (`DISTRICT_ID`) REFERENCES `district` (`DISTRICT_ID`),
  ADD CONSTRAINT `address_ibfk_3` FOREIGN KEY (`WARD_ID`) REFERENCES `ward` (`WARD_ID`);

--
-- Các ràng buộc cho bảng `adopter`
--
ALTER TABLE `adopter`
  ADD CONSTRAINT `adopter_ibfk_1` FOREIGN KEY (`ADOPTION_HISTORY_ID`) REFERENCES `adoption_history` (`ADOPTION_HISTORY_ID`),
  ADD CONSTRAINT `adopter_ibfk_2` FOREIGN KEY (`MARITAL_STATUS_ID`) REFERENCES `marital_status` (`MARITAL_STATUS_ID`),
  ADD CONSTRAINT `adopter_ibfk_3` FOREIGN KEY (`ADOPTER_ADDRESS_ID`) REFERENCES `address` (`ADDRESS_ID`);

--
-- Các ràng buộc cho bảng `adoption_applicant`
--
ALTER TABLE `adoption_applicant`
  ADD CONSTRAINT `adoption_applicant_ibfk_1` FOREIGN KEY (`MARITAL_STATUS_ID`) REFERENCES `marital_status` (`MARITAL_STATUS_ID`),
  ADD CONSTRAINT `adoption_applicant_ibfk_2` FOREIGN KEY (`ADOPTION_APPLICANT_STATUS_ID`) REFERENCES `adoption_applicant_status` (`ADOPTION_APPLICANT_STATUS_ID`),
  ADD CONSTRAINT `adoption_applicant_ibfk_3` FOREIGN KEY (`ADOPTION_APPLICANT_ADDRESS_ID`) REFERENCES `address` (`ADDRESS_ID`);

--
-- Các ràng buộc cho bảng `adoption_applicant_pdf_file`
--
ALTER TABLE `adoption_applicant_pdf_file`
  ADD CONSTRAINT `adoption_applicant_pdf_file_ibfk_1` FOREIGN KEY (`ADOPTION_APPLICANT_ID`) REFERENCES `adoption_applicant` (`ADOPTION_APPLICANT_ID`);

--
-- Các ràng buộc cho bảng `adoption_history`
--
ALTER TABLE `adoption_history`
  ADD CONSTRAINT `adoption_history_ibfk_1` FOREIGN KEY (`CHILDREN_ID`) REFERENCES `children` (`CHILDREN_ID`);

--
-- Các ràng buộc cho bảng `appointment`
--
ALTER TABLE `appointment`
  ADD CONSTRAINT `appointment_ibfk_1` FOREIGN KEY (`ADOPTION_APPLICANT_ID`) REFERENCES `adoption_applicant` (`ADOPTION_APPLICANT_ID`);

--
-- Các ràng buộc cho bảng `article`
--
ALTER TABLE `article`
  ADD CONSTRAINT `article_ibfk_1` FOREIGN KEY (`ARTICLE_CATEGORY_ID`) REFERENCES `article_category` (`ARTICLE_CATEGORY_ID`);

--
-- Các ràng buộc cho bảng `article_category`
--
ALTER TABLE `article_category`
  ADD CONSTRAINT `article_category_ibfk_1` FOREIGN KEY (`PARENT_ID`) REFERENCES `article_category` (`ARTICLE_CATEGORY_ID`);

--
-- Các ràng buộc cho bảng `children`
--
ALTER TABLE `children`
  ADD CONSTRAINT `children_ibfk_1` FOREIGN KEY (`ORPHAN_TYPE_ID`) REFERENCES `orphan_type` (`ORPHAN_TYPE_ID`),
  ADD CONSTRAINT `children_ibfk_2` FOREIGN KEY (`CHILDREN_STATUS_ID`) REFERENCES `children_status` (`CHILDREN_STATUS_ID`),
  ADD CONSTRAINT `children_ibfk_3` FOREIGN KEY (`CHILDREN_ADDRESS_ID`) REFERENCES `address` (`ADDRESS_ID`),
  ADD CONSTRAINT `children_ibfk_4` FOREIGN KEY (`CHILDREN_IMAGE_ID`) REFERENCES `image` (`IMAGE_ID`),
  ADD CONSTRAINT `children_ibfk_5` FOREIGN KEY (`CHILDREN_FAMILY_ID`) REFERENCES `family` (`FAMILY_ID`);

--
-- Các ràng buộc cho bảng `children_document`
--
ALTER TABLE `children_document`
  ADD CONSTRAINT `children_document_ibfk_1` FOREIGN KEY (`DOCUMENT_TYPE_ID`) REFERENCES `document_type` (`DOCUMENT_TYPE_ID`),
  ADD CONSTRAINT `children_document_ibfk_2` FOREIGN KEY (`CHILDREN_ID`) REFERENCES `children` (`CHILDREN_ID`);

--
-- Các ràng buộc cho bảng `children_family_history`
--
ALTER TABLE `children_family_history`
  ADD CONSTRAINT `children_family_history_ibfk_1` FOREIGN KEY (`CHILDREN_ID`) REFERENCES `children` (`CHILDREN_ID`),
  ADD CONSTRAINT `children_family_history_ibfk_2` FOREIGN KEY (`FROM_FAMILY_ID`) REFERENCES `family` (`FAMILY_ID`),
  ADD CONSTRAINT `children_family_history_ibfk_3` FOREIGN KEY (`TO_FAMILY_ID`) REFERENCES `family` (`FAMILY_ID`);

--
-- Các ràng buộc cho bảng `children_relative_relationship`
--
ALTER TABLE `children_relative_relationship`
  ADD CONSTRAINT `children_relative_relationship_ibfk_1` FOREIGN KEY (`CHILDREN_ID`) REFERENCES `children` (`CHILDREN_ID`),
  ADD CONSTRAINT `children_relative_relationship_ibfk_2` FOREIGN KEY (`RELATIVE_ID`) REFERENCES `relative` (`RELATIVE_ID`);

--
-- Các ràng buộc cho bảng `children_status_history`
--
ALTER TABLE `children_status_history`
  ADD CONSTRAINT `children_status_history_ibfk_1` FOREIGN KEY (`CHILDREN_ID`) REFERENCES `children` (`CHILDREN_ID`),
  ADD CONSTRAINT `children_status_history_ibfk_2` FOREIGN KEY (`CHILDREN_STATUS_ID`) REFERENCES `children_status` (`CHILDREN_STATUS_ID`);

--
-- Các ràng buộc cho bảng `district`
--
ALTER TABLE `district`
  ADD CONSTRAINT `district_ibfk_1` FOREIGN KEY (`ADMINISTRATIVE_UNIT_ID`) REFERENCES `administrative_unit` (`ADMINISTRATIVE_UNIT_ID`),
  ADD CONSTRAINT `district_ibfk_2` FOREIGN KEY (`PROVINCE_ID`) REFERENCES `province` (`PROVINCE_ID`);

--
-- Các ràng buộc cho bảng `donation`
--
ALTER TABLE `donation`
  ADD CONSTRAINT `donation_ibfk_1` FOREIGN KEY (`DONOR_ID`) REFERENCES `donor` (`DONOR_ID`);

--
-- Các ràng buộc cho bảng `donor`
--
ALTER TABLE `donor`
  ADD CONSTRAINT `donor_ibfk_1` FOREIGN KEY (`DONOR_ADDRESS_ID`) REFERENCES `address` (`ADDRESS_ID`);

--
-- Các ràng buộc cho bảng `employee`
--
ALTER TABLE `employee`
  ADD CONSTRAINT `employee_ibfk_1` FOREIGN KEY (`EMPLOYEE_ADDRESS_ID`) REFERENCES `address` (`ADDRESS_ID`),
  ADD CONSTRAINT `employee_ibfk_2` FOREIGN KEY (`EMPLOYEE_POSITION_ID`) REFERENCES `employee_position` (`EMPLOYEE_POSITION_ID`);

--
-- Các ràng buộc cho bảng `employee_payroll`
--
ALTER TABLE `employee_payroll`
  ADD CONSTRAINT `employee_payroll_ibfk_1` FOREIGN KEY (`EMPLOYEE_ID`) REFERENCES `employee` (`EMPLOYEE_ID`),
  ADD CONSTRAINT `employee_payroll_ibfk_2` FOREIGN KEY (`SALARY_ID`) REFERENCES `salary` (`SALARY_ID`);

--
-- Các ràng buộc cho bảng `family`
--
ALTER TABLE `family`
  ADD CONSTRAINT `family_ibfk_1` FOREIGN KEY (`FAMILY_CONDITION_ID`) REFERENCES `family_condition` (`FAMILY_CONDITION_ID`);

--
-- Các ràng buộc cho bảng `funding_utilization`
--
ALTER TABLE `funding_utilization`
  ADD CONSTRAINT `funding_utilization_ibfk_1` FOREIGN KEY (`DONATION_ID`) REFERENCES `donation` (`DONATION_ID`),
  ADD CONSTRAINT `funding_utilization_ibfk_2` FOREIGN KEY (`FAMILY_ID`) REFERENCES `family` (`FAMILY_ID`);

--
-- Các ràng buộc cho bảng `medical_record`
--
ALTER TABLE `medical_record`
  ADD CONSTRAINT `medical_record_ibfk_1` FOREIGN KEY (`CHILDREN_ID`) REFERENCES `children` (`CHILDREN_ID`);

--
-- Các ràng buộc cho bảng `mother`
--
ALTER TABLE `mother`
  ADD CONSTRAINT `mother_ibfk_1` FOREIGN KEY (`EMPLOYEE_ID`) REFERENCES `employee` (`EMPLOYEE_ID`),
  ADD CONSTRAINT `mother_ibfk_2` FOREIGN KEY (`FAMILY_ID`) REFERENCES `family` (`FAMILY_ID`);

--
-- Các ràng buộc cho bảng `province`
--
ALTER TABLE `province`
  ADD CONSTRAINT `province_ibfk_1` FOREIGN KEY (`ADMINISTRATIVE_REGION_ID`) REFERENCES `administrative_region` (`ADMINISTRATIVE_REGION_ID`),
  ADD CONSTRAINT `province_ibfk_2` FOREIGN KEY (`ADMINISTRATIVE_UNIT_ID`) REFERENCES `administrative_unit` (`ADMINISTRATIVE_UNIT_ID`);

--
-- Các ràng buộc cho bảng `relative`
--
ALTER TABLE `relative`
  ADD CONSTRAINT `relative_ibfk_1` FOREIGN KEY (`RELATIVE_ADDRESS_ID`) REFERENCES `address` (`ADDRESS_ID`);

--
-- Các ràng buộc cho bảng `salary`
--
ALTER TABLE `salary`
  ADD CONSTRAINT `salary_ibfk_1` FOREIGN KEY (`EMPLOYEE_ID`) REFERENCES `employee` (`EMPLOYEE_ID`),
  ADD CONSTRAINT `salary_ibfk_2` FOREIGN KEY (`EMPLOYEE_POSITION_ID`) REFERENCES `employee_position` (`EMPLOYEE_POSITION_ID`);

--
-- Các ràng buộc cho bảng `token`
--
ALTER TABLE `token`
  ADD CONSTRAINT `token_ibfk_1` FOREIGN KEY (`ADOPTION_APPLICANT_ID`) REFERENCES `adoption_applicant` (`ADOPTION_APPLICANT_ID`);

--
-- Các ràng buộc cho bảng `vaccination_record`
--
ALTER TABLE `vaccination_record`
  ADD CONSTRAINT `vaccination_record_ibfk_1` FOREIGN KEY (`CHILDREN_ID`) REFERENCES `children` (`CHILDREN_ID`);

--
-- Các ràng buộc cho bảng `ward`
--
ALTER TABLE `ward`
  ADD CONSTRAINT `ward_ibfk_1` FOREIGN KEY (`ADMINISTRATIVE_UNIT_ID`) REFERENCES `administrative_unit` (`ADMINISTRATIVE_UNIT_ID`),
  ADD CONSTRAINT `ward_ibfk_2` FOREIGN KEY (`DISTRICT_ID`) REFERENCES `district` (`DISTRICT_ID`);

--
-- Các ràng buộc cho bảng `role_permission`
--
ALTER TABLE `role_permission`
  ADD CONSTRAINT `role_permission_ibfk_1` FOREIGN KEY (`ROLE_ID`) REFERENCES `role` (`ROLE_ID`),
  ADD CONSTRAINT `role_permission_ibfk_2` FOREIGN KEY (`PERMISSION_ID`) REFERENCES `permission` (`PERMISSION_ID`);

--
-- Các ràng buộc cho bảng `user_role`
--
ALTER TABLE `user_role`
  ADD CONSTRAINT `user_role_ibfk_1` FOREIGN KEY (`USER_ID`) REFERENCES `user` (`USER_ID`),
  ADD CONSTRAINT `user_role_ibfk_2` FOREIGN KEY (`ROLE_ID`) REFERENCES `role` (`ROLE_ID`);

--
-- Các ràng buộc cho bảng `user_token`
--
ALTER TABLE `user_token`
  ADD CONSTRAINT `user_token_ibfk_1` FOREIGN KEY (`USER_ID`) REFERENCES `user` (`USER_ID`);