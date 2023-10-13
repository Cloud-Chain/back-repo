package pnu.cse.cloudchain.contract.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AssignDto {
    private String name;
    private String residentRegistrationNumber;
    private String phoneNumber;
    private String address;

    public String getString() {
        String ret = "\n -- Name - "+name
                    +"\n -- ResidentRegistrationNumber - "+residentRegistrationNumber
                    +"\n -- PhoneNumber - "+phoneNumber
                    +"\n -- Address - "+address;
        return  ret;
    }
}

