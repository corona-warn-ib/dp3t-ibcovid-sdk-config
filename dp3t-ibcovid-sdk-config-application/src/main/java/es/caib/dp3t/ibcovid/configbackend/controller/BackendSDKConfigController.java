package es.caib.dp3t.ibcovid.configbackend.controller;

import es.caib.dp3t.ibcovid.configbackend.controller.mapper.BackendSDKConfigDtoMapper;
import es.caib.dp3t.ibcovid.configbackend.controller.model.backendsdkconfig.BackendSDKConfigDto;
import es.caib.dp3t.ibcovid.configbackend.controller.model.backendsdkconfig.InfoBoxCollectionDto;
import es.caib.dp3t.ibcovid.configbackend.controller.model.backendsdkconfig.InfoBoxDto;
import es.caib.dp3t.ibcovid.configbackend.controller.model.backendsdkconfig.SDKConfigDto;
import es.caib.dp3t.ibcovid.configbackend.service.BackendSDKConfigService;
import io.swagger.annotations.Api;
import lombok.extern.log4j.Log4j2;
import es.caib.dp3t.ibcovid.configbackend.controller.config.RouteConstants;
import es.caib.dp3t.ibcovid.configbackend.service.model.sdkbackendconfig.BackendSDKConfigSrvDto;
import org.springframework.http.CacheControl;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;

@RestController
@RequestMapping(value = RouteConstants.SDK_CONFIG_BASE_PATH, produces = {MediaType.APPLICATION_JSON_VALUE})
@Log4j2
@Api(tags = "Backend SDK configuration operations for the mobile app")
public class BackendSDKConfigController {

    private final BackendSDKConfigService backendSDKConfigService;

    public BackendSDKConfigController(final BackendSDKConfigService backendSDKConfigService) {
        this.backendSDKConfigService = backendSDKConfigService;
    }

    @GetMapping(value = "/config")
    public @ResponseBody
    ResponseEntity<BackendSDKConfigDto> getConfig(@RequestParam(required = true) final String appversion,
                                                  @RequestParam(required = true) final String osversion,
                                                  @RequestParam(required = true) final String buildnr) {
        final BackendSDKConfigSrvDto srvResponse = backendSDKConfigService
                .getBackendSDKConfigForMobileApp(appversion, osversion, buildnr);
        final BackendSDKConfigDto config = BackendSDKConfigDtoMapper.INSTANCE.srvDtoToDto(srvResponse);

        // Build nr of the initial iOS pilot test app. Contains bug, that factors are
        // not used correctly in contact calculations. Set factorHigh to 0.0 for
        // improving the calculation.
        if (buildnr.equals("ios-200524.1316.87")) {
            config.getIosGaenSdkConfig().setFactorHigh(0.0d);
        }
        return ResponseEntity.ok().cacheControl(CacheControl.maxAge(Duration.ofMinutes(5))).body(config);
    }

    @GetMapping(value = "/testinfobox/config")
    public @ResponseBody
    ResponseEntity<BackendSDKConfigDto> getGhettoboxConfig(@RequestParam(required = true) final String appversion,
                                                           @RequestParam(required = true) final String osversion,
                                                           @RequestParam(required = true) final String buildnr) {
        final BackendSDKConfigDto body = mockConfigResponseWithInfoBox();
        return ResponseEntity.ok(body);
    }

    private BackendSDKConfigDto mockConfigResponseWithInfoBox() {
        final BackendSDKConfigDto configResponse = new BackendSDKConfigDto();

        final InfoBoxDto infoBoxde = new InfoBoxDto();
        infoBoxde.setMsg("Hier steht ein Text. Das kann ein Hinweis sein. Je länger umso mehr Platz DE");
        infoBoxde.setTitle("Hinweis DE");
        infoBoxde.setUrlTitle("Und ein externer Link DE");
        infoBoxde.setUrl("https://www.bag.admin.ch/bag/de/home.html");
        final InfoBoxDto infoBoxfr = new InfoBoxDto();
        infoBoxfr.setMsg("Hier steht ein Text. Das kann ein Hinweis sein. Je länger umso mehr Platz FR");
        infoBoxfr.setTitle("Hinweis FR");
        infoBoxfr.setUrlTitle("Und ein externer Link FR");
        infoBoxfr.setUrl("https://www.bag.admin.ch/bag/fr/home.html");
        final InfoBoxDto infoBoxit = new InfoBoxDto();
        infoBoxit.setMsg("Hier steht ein Text. Das kann ein Hinweis sein. Je länger umso mehr Platz IT");
        infoBoxit.setTitle("Hinweis IT");
        infoBoxit.setUrlTitle("Und ein externer Link IT");
        infoBoxit.setUrl("https://www.bag.admin.ch/bag/it/home.html");
        final InfoBoxDto infoBoxen = new InfoBoxDto();
        infoBoxen.setMsg("Hier steht ein Text. Das kann ein Hinweis sein. Je länger umso mehr Platz EN");
        infoBoxen.setTitle("Hinweis EN");
        infoBoxen.setUrlTitle("Und ein externer Link EN");
        infoBoxen.setUrl("https://www.bag.admin.ch/bag/en/home.html");
        final InfoBoxDto infoBoxpt = new InfoBoxDto();
        infoBoxpt.setMsg("Hier steht ein Text. Das kann ein Hinweis sein. Je länger umso mehr Platz PT");
        infoBoxpt.setTitle("Hinweis PT");
        infoBoxpt.setUrlTitle("Und ein externer Link PT");
        infoBoxpt.setUrl("https://www.bag.admin.ch/bag/pt/home.html");
        final InfoBoxDto infoBoxes = new InfoBoxDto();
        infoBoxes.setMsg("Hier steht ein Text. Das kann ein Hinweis sein. Je länger umso mehr Platz ES");
        infoBoxes.setTitle("Hinweis ES");
        infoBoxes.setUrlTitle("Und ein externer Link ES");
        infoBoxes.setUrl("https://www.bag.admin.ch/bag/en/home.html");
        final InfoBoxDto infoBoxsq = new InfoBoxDto();
        infoBoxsq.setMsg("Hier steht ein Text. Das kann ein Hinweis sein. Je länger umso mehr Platz SQ");
        infoBoxsq.setTitle("Hinweis SQ");
        infoBoxsq.setUrlTitle("Und ein externer Link SQ");
        infoBoxsq.setUrl("https://www.bag.admin.ch/bag/en/home.html");
        final InfoBoxDto infoBoxbs = new InfoBoxDto();
        infoBoxbs.setMsg("Hier steht ein Text. Das kann ein Hinweis sein. Je länger umso mehr Platz BS");
        infoBoxbs.setTitle("Hinweis BS");
        infoBoxbs.setUrlTitle("Und ein externer Link BS");
        infoBoxbs.setUrl("https://www.bag.admin.ch/bag/en/home.html");
        final InfoBoxDto infoBoxhr = new InfoBoxDto();
        infoBoxhr.setMsg("Hier steht ein Text. Das kann ein Hinweis sein. Je länger umso mehr Platz HR");
        infoBoxhr.setTitle("Hinweis HR");
        infoBoxhr.setUrlTitle("Und ein externer Link HR");
        infoBoxhr.setUrl("https://www.bag.admin.ch/bag/en/home.html");
        final InfoBoxDto infoBoxrm = new InfoBoxDto();
        infoBoxrm.setMsg("Hier steht ein Text. Das kann ein Hinweis sein. Je länger umso mehr Platz RM");
        infoBoxrm.setTitle("Hinweis RM");
        infoBoxrm.setUrlTitle("Und ein externer Link RM");
        infoBoxrm.setUrl("https://www.bag.admin.ch/bag/en/home.html");
        final InfoBoxDto infoBoxsr = new InfoBoxDto();
        infoBoxsr.setMsg("Hier steht ein Text. Das kann ein Hinweis sein. Je länger umso mehr Platz SR");
        infoBoxsr.setTitle("Hinweis SR");
        infoBoxsr.setUrlTitle("Und ein externer Link SR");
        infoBoxsr.setUrl("https://www.bag.admin.ch/bag/en/home.html");

        final InfoBoxCollectionDto collection = new InfoBoxCollectionDto();
        collection.setDeInfoBox(infoBoxde);
        collection.setEnInfoBox(infoBoxen);
        collection.setFrInfoBox(infoBoxfr);
        collection.setItInfoBox(infoBoxit);
        collection.setPtInfoBox(infoBoxpt);
        collection.setEsInfoBox(infoBoxes);
        collection.setSqInfoBox(infoBoxsq);
        collection.setHrInfoBox(infoBoxhr);
        collection.setBsInfoBox(infoBoxbs);
        collection.setRmInfoBox(infoBoxrm);
        collection.setSrInfoBox(infoBoxsr);
        configResponse.setInfoBox(collection);

        final SDKConfigDto config = new SDKConfigDto();
        configResponse.setSdkConfig(config);
        return configResponse;
    }

    public BackendSDKConfigDto mockConfigResponseWithForceUpdate() {
        final BackendSDKConfigDto configResponse = new BackendSDKConfigDto();
        configResponse.setForceUpdate(true);
        return configResponse;
    }


}
