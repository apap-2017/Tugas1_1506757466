package com.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KelurahanModel {
	private Integer id;
	private String kode_kelurahan;
	private Integer id_kecamatan;
	private String nama_kelurahan;
	private String kode_pos;
	
	private KecamatanModel kecamatan;
}
