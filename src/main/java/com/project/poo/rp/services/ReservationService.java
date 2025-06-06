package com.project.poo.rp.services;

import com.project.poo.rp.dtos.*;
import com.project.poo.rp.entities.*;
import com.project.poo.rp.enumerations.DbOperationStatus;
import com.project.poo.rp.enumerations.EquipmentReturnStatus;
import com.project.poo.rp.enumerations.EquipmentStatus;
import com.project.poo.rp.repositories.*;
import com.project.poo.rp.status.DatabaseOperationStatus;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class ReservationService {
    @Autowired
    ReservationRepository reservationRepository;
    @Autowired
    EquipmentRepository equipmentRepository;
    @Autowired
    ConsommableRepository consommableRepository;
    @Autowired
    ConsommableReservationRepository consommableReservationRepository;
    @Autowired
    EquipmentReservationRepository equipmentReservationRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    EquipmentReservationService equipmentReservationService;

    @Transactional
    public DatabaseOperationStatus createReservation(ReservationCreateDto dto) {
        Reservation reservation = new Reservation();
        Optional<User> user = userRepository.findById(dto.getUserId());
        List<EquipmentReservation> equipmentReservations = new ArrayList<>();
        List<ConsommableReservation> consommableReservations = new ArrayList<>();

        if (user.isEmpty()) {
            return new DatabaseOperationStatus(DbOperationStatus.NOT_FOUND, null, "User not found");
        }

        for (Long equipmentId : dto.getEquipmentIds()) {
            Optional<Equipment> equipment = equipmentRepository.findById(equipmentId);
            if (equipment.isEmpty()) {
                throw new RuntimeException(String.format("Equipment with id : %d not found", equipmentId));
            }
            Equipment equipmentEntity = equipment.get();
            equipmentEntity.setEtat(EquipmentStatus.INCONNU);
            equipmentEntity.setLocalisation(dto.getLocalisation());

            EquipmentReservation equipmentReservation = new EquipmentReservation();
            equipmentReservation.setEquipment(equipmentRepository.save(equipmentEntity));
            equipmentReservation.setDateRetour(null);
            equipmentReservation.setEtatRetour(EquipmentReturnStatus.NON_RETOURNE);
            equipmentReservation.setReservation(reservation);
            equipmentReservations.add(equipmentReservation);
        }

        for (ConsommableReservationCreateDto cDto : dto.getConsommables()) {
            Optional<Consommable> consommable = consommableRepository.findById(cDto.getId());
            if (consommable.isEmpty()) {
                throw new RuntimeException(String.format("Consommable with id : %d not found", cDto.getId()));
            }
            Consommable consommableEntity = consommable.get();
            consommableEntity.setStockActuel(consommableEntity.getStockActuel() - cDto.getQuantity());

            ConsommableReservation consommableReservation = new ConsommableReservation();
            consommableReservation.setConsommable(consommableRepository.save(consommableEntity));
            consommableReservation.setQuantiteDemandee(cDto.getQuantity());
            consommableReservation.setReservation(reservation);
            consommableReservations.add(consommableReservation);
        }

        reservation.setUser(user.get());
        reservation.setConsommableReservations(consommableReservations);
        reservation.setEquipmentReservations(equipmentReservations);
        reservation.setStart(LocalDateTime.now());
        reservation.setEnd(null);

        return new DatabaseOperationStatus(
                DbOperationStatus.SUCCESS,
                mapToDto(reservationRepository.save(reservation)),
                "Reservation created successfully"
        );
    }

    @Transactional
    public DatabaseOperationStatus updateReservation(Long idReservation,ReservationUpdateDto dto){
        Optional<Reservation> reservation = reservationRepository.findById(idReservation);
        if(reservation.isEmpty()){
            return new DatabaseOperationStatus(DbOperationStatus.NOT_FOUND,null,"Reservation not found");
        }

        for (EquipmentReservation equipmentReservation : reservation.get().getEquipmentReservations()) {
            for (EquipmentReservationUpdateDto ERDto : dto.getERUpdate()) {
                if (equipmentReservation.getId().equals(ERDto.getERId())) {
                    equipmentReservationService.updateReservation(equipmentReservation.getId(),ERDto.getEquipmentStatus());
                    break;
                }
            }
        }
        return new DatabaseOperationStatus(DbOperationStatus.SUCCESS,mapToDto(reservationRepository.findById(idReservation).get()),"Reservation updated Successfully");
    }

    public List<ReservationDto> findAll(){
        List<Reservation> reservationList = reservationRepository.findAll();
        List<ReservationDto> dtos =new ArrayList<>();
        for (Reservation r:reservationList){
            ReservationDto reservationDto = mapToDto(r);
            dtos.add(reservationDto);
        }
        dtos.sort(Comparator.comparing(ReservationDto::getStartDate).reversed());
        return dtos;
    }

    public ReservationDto findById(Long id){
        Optional<Reservation> reservation = reservationRepository.findById(id);
        if(reservation.isEmpty()){
            return null;
        }
        return mapToDto(reservation.get());
    }
    private ReservationDto mapToDto(Reservation reservation){
        ReservationDto dto = new ReservationDto();
        List<ConsommableReservationDto> CRDtos = new ArrayList<>();
        List<EquipmentReservationDto> ERDtos = new ArrayList<>();
        for (ConsommableReservation cr : reservation.getConsommableReservations()){
            ConsommableReservationDto CRDto = new ConsommableReservationDto();
            CRDto.setId(cr.getId());
            CRDto.setQuantity(cr.getQuantiteDemandee());
            CRDto.setIdConsommable(cr.getConsommable().getId());
            CRDto.setTypeConsommable(cr.getConsommable().getType().getNom());
            CRDtos.add(CRDto);
        }


        for(EquipmentReservation er : reservation.getEquipmentReservations()){
            EquipmentReservationDto ERDto = new EquipmentReservationDto();
            ERDto.setId(er.getId());
            ERDto.setEquipmentId(er.getEquipment().getId());
            ERDto.setEquipmentCode(er.getEquipment().getCode());
            ERDto.setReturnStatus(er.getEtatRetour());
            ERDto.setEquipmentStatus(er.getEquipment().getEtat());
            ERDto.setEquipmentType(er.getEquipment().getType().getNom());
            ERDtos.add(ERDto);
        }
        dto.setCRDto(CRDtos);
        dto.setERDto(ERDtos);
        dto.setUserId(reservation.getUser().getId());
        dto.setUsername(reservation.getUser().getUsername());
        dto.setId(reservation.getId());
        dto.setStartDate(reservation.getStart());
        return dto;
    }
}
