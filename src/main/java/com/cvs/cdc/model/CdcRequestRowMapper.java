/*
 * Copyright 2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.cvs.cdc.model;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;


public class CdcRequestRowMapper implements RowMapper<CdcRequestToApi> {
	@Override
	public CdcRequestToApi mapRow(ResultSet resultSet, int i) throws SQLException {
		return new CdcRequestToApi(resultSet.getString("vax_event_id"),
				resultSet.getString("RXC_IMM_ID"),
				resultSet.getString("EXTR_DT"),
				resultSet.getString("JOB_NM"),
				resultSet.getString("ext_type")
				);
	}
}
