package com.wuweibi.bullet.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.wuweibi.bullet.builder.MapBuilder;
import com.wuweibi.bullet.domain.dto.DeviceMappingDto;
import com.wuweibi.bullet.entity.Device;
import com.wuweibi.bullet.entity.DeviceMapping;
import com.wuweibi.bullet.mapper.DeviceMappingMapper;
import com.wuweibi.bullet.service.DeviceMappingService;
import com.wuweibi.bullet.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.wuweibi.bullet.builder.MapBuilder.newMap;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author marker
 * @since 2017-12-09
 */
@Service
public class DeviceMappingServiceImpl extends ServiceImpl<DeviceMappingMapper, DeviceMapping> implements DeviceMappingService {

    @Autowired
    private DeviceService deviceService;

    @Override
    public boolean existsDomain(String domain) {
        return this.baseMapper.existsDomain(newMap(1)
                .setParam("domain", domain).build());
    }

    @Override
    public boolean exists(Long userId, Long id) {

        return this.baseMapper.exists(newMap(2)
                .setParam("userId", userId)
                .setParam("id", id)
                .build());
    }

    @Override
    public DeviceMappingDto getMapping(String host) {
        // TODO host处理


        DeviceMapping params = new DeviceMapping();
        params.setDomain(host);

        DeviceMapping deviceMapping = this.baseMapper.selectOne(params);
        if(deviceMapping == null){
            return null;
        }
        long deviceId = deviceMapping.getDeviceId();
        Device device = deviceService.selectById(deviceId);


        DeviceMappingDto dto = new DeviceMappingDto();
        dto.setDeviceCode(device.getDeviceId());
        dto.setPort(deviceMapping.getPort());
        dto.setProtocol(deviceMapping.getProtocol());
        dto.setHost(deviceMapping.getHost());

        return dto;
    }

    @Override
    public void deleteByDeviceId(Long deviceId) {
        this.baseMapper.deleteByMap(MapBuilder.newMap(1).setParam(
                "device_id", deviceId
        ).build());
    }

    @Override
    public String getDeviceNo(Long deviceId) {


       return this.baseMapper.selectDeviceNo( deviceId );
    }

    @Override
    public List<DeviceMapping> getAll() {
        return this.baseMapper.selectList(null);
    }
}
