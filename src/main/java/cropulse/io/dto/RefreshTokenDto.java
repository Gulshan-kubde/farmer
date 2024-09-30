package cropulse.io.dto;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class RefreshTokenDto {
	private String token;

}
