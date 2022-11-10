package com.udacity.jdnd.course3.critter.utils;

import com.udacity.jdnd.course3.critter.schedule.Schedule;
import com.udacity.jdnd.course3.critter.schedule.ScheduleDTO;
import org.springframework.beans.BeanUtils;

public class ScheduleMapper implements DtoEntityMapper<Schedule, ScheduleDTO> {
    @Override
    public Schedule fromDtoToEntity(ScheduleDTO dto) {
        Schedule schedule = new Schedule();
        BeanUtils.copyProperties(dto,schedule);
        return schedule;
    }

    @Override
    public ScheduleDTO fromEntityToDto(Schedule entity) {
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        BeanUtils.copyProperties(entity,scheduleDTO);
        return scheduleDTO;
    }
}
