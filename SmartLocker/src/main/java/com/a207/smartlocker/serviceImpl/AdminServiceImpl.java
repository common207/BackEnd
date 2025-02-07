package com.a207.smartlocker.serviceImpl;

import com.a207.smartlocker.model.dto.AdminLoginRequest;
import com.a207.smartlocker.model.dto.AdminLoginResponse;
import com.a207.smartlocker.model.entity.Certification;
import com.a207.smartlocker.model.entity.Robot;
import com.a207.smartlocker.repository.CertificationRepository;
import com.a207.smartlocker.repository.RobotRepository;
import com.a207.smartlocker.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final CertificationRepository certificationRepository;
    private final RobotRepository robotRepository;

    @Override
    public AdminLoginResponse login(AdminLoginRequest request) {
        Optional<Certification> certificationOpt = certificationRepository
                .findByAdminIdAndAdminPassword(request.getAdminId(), request.getAdminPassword());

        if (certificationOpt.isPresent()) {
            return new AdminLoginResponse(true, "로그인 성공");
        }
        return new AdminLoginResponse(false, "아이디 또는 비밀번호가 일치하지 않습니다");
    }

    @Override
    public List<Robot> getAllRobots() {
        return robotRepository.findAll();
    }
}