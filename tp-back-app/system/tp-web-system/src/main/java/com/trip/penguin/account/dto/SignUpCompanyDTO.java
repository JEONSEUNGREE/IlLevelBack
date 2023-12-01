package com.trip.penguin.account.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignUpCompanyDTO {

    private String com_nm;

    private String comEmail;

    private String comPwd;

    private String comAddress;

}
