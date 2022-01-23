package PNoKio.Server.api;

import PNoKio.Server.domain.Store;
import PNoKio.Server.dto.APIMenuDto;
import PNoKio.Server.dto.RequestMenuDto;
import PNoKio.Server.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class APIController {

    private final StoreService storeService;
    private final RestTemplate restTemplate;
    @GetMapping("/")
    public List<APIMenuDto> getCategoryAndItem(@RequestParam (name = "storeId") Long storeId){
        Store store = storeService.findCategoryAndItem(storeId).get();
        return store.getCategories().stream().map(a -> new APIMenuDto(a.getCategoryName(),a.getItems()))
                .collect(Collectors.toList());
    }

    @PostMapping("/")
    public ResponseEntity<String> postKiosk(@RequestBody List<RequestMenuDto> requestMenuDto){
        HttpEntity<List<RequestMenuDto>> request = new HttpEntity<>(requestMenuDto);
        return restTemplate.exchange("kioskurl", HttpMethod.POST, request,String.class);
    }
}