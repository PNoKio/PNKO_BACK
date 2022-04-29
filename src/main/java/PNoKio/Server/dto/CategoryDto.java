package PNoKio.Server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
public class CategoryDto {
    @NotEmpty(message= "공란으로 제출할 수 없습니다.")
    private String categoryName;
}
