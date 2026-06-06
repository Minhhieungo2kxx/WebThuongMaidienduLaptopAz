package vn.ecornomere.ecornomereAZ.repository;

import java.time.Instant;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import org.springframework.transaction.annotation.Transactional;
import vn.ecornomere.ecornomereAZ.model.entity.TemporaryUpload;

import java.util.List;
import java.util.Optional;

public interface TemporaryUploadRepository extends JpaRepository<TemporaryUpload, Long> {
      Optional<TemporaryUpload> findByPublicId(String publicId);

//      List<TemporaryUpload> findByUsedFalseAndCreatedAtBefore(Instant cutoff);
      List<TemporaryUpload> findTop100ByUsedFalseAndCreatedAtBeforeOrderByIdAsc(Instant cutoff);

      @Modifying
      @Transactional
      @Query("""
                  update TemporaryUpload t
                  set t.used = true
                  where t.publicId = :publicId
                  """)
      int markAsUsed(@Param("publicId") String publicId);

      @Modifying
      @Transactional
      @Query("""
                      update TemporaryUpload t
                      set t.used = false
                      where t.publicId = :publicId
                  """)
      int markAsUnused(@Param("publicId") String publicId);

}
