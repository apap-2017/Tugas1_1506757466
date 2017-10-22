package com.example.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.model.*;
import com.example.service.CitizenshipService;

@Controller
public class CitizenshipController {	
	@Autowired
    CitizenshipService citizenshipDAO;

    @RequestMapping("/")
    public String index()
    {
        return "index";
    }
    
    @RequestMapping("/penduduk")
    public String viewPenduduk (@RequestParam(value="nik") String nik, Model model) {
        PendudukModel penduduk = citizenshipDAO.selectPenduduk(nik);
        if (penduduk != null) {
            model.addAttribute ("penduduk", penduduk);
            return "penduduk";
        } else {
        	model.addAttribute ("nik", nik);
            return "penduduk-not-found";
        }
    }
    
    @RequestMapping("/keluarga")
    public String viewKeluarga (@RequestParam(value="nkk") String nkk, Model model) {
    	KeluargaModel keluarga = citizenshipDAO.selectKeluarga(nkk);
    	if (keluarga != null && keluarga.getIs_tidak_berlaku() == 0) {
    		ArrayList<PendudukModel> pendudukList = citizenshipDAO.selectPendudukByIdKeluarga(keluarga.getId());
    		
    		model.addAttribute ("keluarga", keluarga);
    		model.addAttribute ("pendudukList", pendudukList);
    		return "keluarga";
    	} else {
    		model.addAttribute ("nkk", nkk);
    		return "keluarga-not-found";
    	}
    	
    }
    
    @RequestMapping("/keluarga/tambah")
    public String createKeluargaRequest(Model model, 
    		@RequestParam(value = "alamat", required = false) String alamat,
            @RequestParam(value = "rt", required = false) String rt,
            @RequestParam(value = "rw", required = false) String rw,
            @RequestParam(value = "kelurahan", required = false) Integer kelurahan,
            @RequestParam(value = "kecamatan", required = false) Integer kecamatan,
            @RequestParam(value = "kota", required = false) Integer kota
            ) {
    	
    	model.addAttribute("alamat", alamat);
    	model.addAttribute("rt", rt);
    	model.addAttribute("rw", rw);
    	model.addAttribute("kotaSelected", kota);
    	
    	ArrayList<KotaModel> listOfKota = citizenshipDAO.selectAllKota();
    	model.addAttribute("listOfKota", listOfKota);
    	
    	if (kota != null) {
    		ArrayList<KecamatanModel> listOfKec = citizenshipDAO.selectAllKecamatanByIdKota(kota);
    		model.addAttribute("listOfKec", listOfKec);
    	} 
    	
    	if (kecamatan != null) {
    		ArrayList<KelurahanModel> listOfKel = citizenshipDAO.selectAllKelurahanByIdKecamatan(kecamatan);
    		model.addAttribute("listOfKel", listOfKel);
    	}
    	
    	return "keluarga-add";
    }
    
    @PostMapping("/keluarga/tambah")
    public String submitCreateKeluarga(Model model,
    		@RequestParam(value = "alamat", required = false) String alamat,
            @RequestParam(value = "rt", required = false) String rt,
            @RequestParam(value = "rw", required = false) String rw,
            @RequestParam(value = "kelurahan", required = false) Integer kelurahan,
            @RequestParam(value = "kecamatan", required = false) Integer kecamatan,
            @RequestParam(value = "kota", required = false) Integer kota,
            @RequestParam(value = "isSubmit", required = false) Integer isSubmit
    		) {
    	
    	if (isSubmit == 0) {
    		return createKeluargaRequest(model, alamat, rt, rw, kelurahan, kecamatan, kota);
    	}
    	
    	if (alamat.equals("") || rt.equals("") || rw.equals("")) {
    		return "keluarga-add-failed";
    	}
    	
    	String newKeluargaKodeLokasi = citizenshipDAO.selectKelurahanById(kelurahan).getKode_kelurahan().substring(0, 6);
    	String timeStamp = new SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date());
    	String newKeluargaDOCCode = timeStamp.substring(2,4) + timeStamp.substring(5,7) + timeStamp.substring(8,10); //YYYY-MM-DD
    	Integer newKeluargaSuffixCode = citizenshipDAO.selectKeluargaByPartialNKK(newKeluargaKodeLokasi + newKeluargaDOCCode + "%") + 1;
    	String newKeluargaSuffixCodeString = String.format("%04d", newKeluargaSuffixCode);
    	
    	String newKeluargaNKK = "" + newKeluargaKodeLokasi + newKeluargaDOCCode + newKeluargaSuffixCodeString;
    	
    	Integer newKeluargaId = citizenshipDAO.selectLargestKeluargaId() + 1;
    	
    	KeluargaModel newKm = new KeluargaModel(newKeluargaId, newKeluargaNKK, alamat, rt, rw, kelurahan, 0, null, null);
    	citizenshipDAO.insertKeluarga(newKm);
    	
    	model.addAttribute("nkk", newKeluargaNKK);
    	return "keluarga-add-success";
    }
    
    @RequestMapping("/keluarga/ubah")
    public String updateKeluargaRequest(Model model, @RequestParam(value="nkkToUpdate") String nkk) {
    	KeluargaModel km = citizenshipDAO.selectKeluarga(nkk);
    	if (km != null) {
    		ArrayList<KotaModel> listOfKota = citizenshipDAO.selectAllKota();
    		model.addAttribute("keluarga", km);
        	model.addAttribute("listOfKota", listOfKota);
        	return "keluarga-ubah";
    	} else {
    		return "keluarga-ubah-failed";
    	}
    }
    
    @RequestMapping("/api/getKecamatan")
    public @ResponseBody String apiKecamatan(Model model, @RequestParam(value="kota") Integer kota) {
    	ArrayList<KecamatanModel> listOfKec = citizenshipDAO.selectAllKecamatanByIdKota(kota);
    	String encodedKec = "";
    	for (KecamatanModel kecamatanModel : listOfKec) {
			encodedKec += kecamatanModel.getId() + "#" + kecamatanModel.getKode_kecamatan() + "#" + kecamatanModel.getNama_kecamatan() + "%";
		}
    	return encodedKec;
    }
    
    @RequestMapping("/api/getKelurahan")
    public @ResponseBody String apiKelurahan(Model model, @RequestParam(value="kecamatan") Integer kecamatan) {
    	ArrayList<KelurahanModel> listOfKel = citizenshipDAO.selectAllKelurahanByIdKecamatan(kecamatan);
    	String encodedKel = "";
    	for (KelurahanModel kelurahanModel : listOfKel) {
			encodedKel += kelurahanModel.getId() + "#" + kelurahanModel.getKode_kelurahan() + "#" + kelurahanModel.getNama_kelurahan() + "%";
		}
    	return encodedKel;
    }
    
    @PostMapping("/keluarga/ubah")
    public String submitUpdateKeluarga(Model model,
    		@RequestParam(value = "alamat", required = false) String alamat,
    		@RequestParam(value = "idToUpdate", required = false) Integer idToUpdate,
    		@RequestParam(value = "oldNkk", required = false) String oldNkk,
            @RequestParam(value = "rt", required = false) String rt,
            @RequestParam(value = "rw", required = false) String rw,
            @RequestParam(value = "kelurahan", required = false) Integer kelurahan,
            @RequestParam(value = "kecamatan", required = false) Integer kecamatan,
            @RequestParam(value = "kota", required = false) Integer kota
    		) {
    	
    	if (alamat.equals("") || rt.equals("") || rw.equals("")) {
    		return "keluarga-ubah-failed";
    	}
    	
    	String newKeluargaKodeLokasi = citizenshipDAO.selectKelurahanById(kelurahan).getKode_kelurahan().substring(0, 6);
    	String newKeluargaDOCCode = oldNkk.substring(6,12);
    	Integer newKeluargaSuffixCode = citizenshipDAO.selectKeluargaByPartialNKK(newKeluargaKodeLokasi + newKeluargaDOCCode + "%") + 1;
    	String newKeluargaSuffixCodeString = String.format("%04d", newKeluargaSuffixCode);
    	
    	String newKeluargaNKK = "" + newKeluargaKodeLokasi + newKeluargaDOCCode + newKeluargaSuffixCodeString;
	
    	KeluargaModel newKm = new KeluargaModel(idToUpdate, newKeluargaNKK, alamat, rt, rw, kelurahan, 0, null, null);
    	citizenshipDAO.updateKeluargaByNKK(newKm);
    	
    	model.addAttribute("nkk", oldNkk);
    	model.addAttribute("nkkNew", newKeluargaNKK);
    	return "keluarga-ubah-success";
    }
    
    @RequestMapping("/penduduk/tambah")
    public String createPendudukRequest(Model model) {
    	ArrayList<KeluargaModel> listOfKm = citizenshipDAO.selectAllKeluarga();
    	model.addAttribute("listOfKm", listOfKm);
    	return "penduduk-add";
    }
    
    @PostMapping("/penduduk/tambah")
    public String submitCreatePenduduk(Model model,
    		@RequestParam(value = "nama", required = false) String nama,
            @RequestParam(value = "tempat_lahir", required = false) String tempat_lahir,
            @RequestParam(value = "tanggal_lahir", required = false) String tanggal_lahir,
            @RequestParam(value = "jenis_kelamin", required = false) Integer jenis_kelamin,
            @RequestParam(value = "is_wni", required = false) Integer is_wni,
            @RequestParam(value = "id_keluarga", required = false) Integer id_keluarga,
            @RequestParam(value = "agama", required = false) String agama,
            @RequestParam(value = "pekerjaan", required = false) String pekerjaan,
            @RequestParam(value = "status_perkawinan", required = false) String status_perkawinan,
            @RequestParam(value = "golongan_darah", required = false) String golongan_darah,
            @RequestParam(value = "status_dalam_keluarga", required = false) String status_dalam_keluarga,
            @RequestParam(value = "is_wafat", required = false) Integer is_wafat
    		) {
    	
    	if(nama.equals("") || tempat_lahir.equals("") || tanggal_lahir.equals("") || agama.equals("") || pekerjaan.equals("") || status_dalam_keluarga.equals("") || status_perkawinan.equals("") || golongan_darah.equals("")) {
    		return "penduduk-add-failed";
    	}
    	
    	KeluargaModel km = citizenshipDAO.selectKeluargaById(id_keluarga);
    	if (km != null) {
        	String newPendudukKodeLokasi = km.getKelurahan().getKode_kelurahan().substring(0, 6);
        	
        	String hh = tanggal_lahir.substring(8);
        	String bb = tanggal_lahir.substring(5, 7);
        	String tt = tanggal_lahir.substring(2, 4);
        	
        	if (jenis_kelamin == 1) {
        		hh = "" + (Integer.parseInt(hh) + 40);
        	}
        	
        	String newPendudukDOBCode = hh + bb + tt; //YYYY-MM-DD
        	Integer newPendudukSuffixCode = citizenshipDAO.selectPendudukByGenderIncrement(newPendudukKodeLokasi + newPendudukDOBCode + "%") + 1;
        	String newPendudukSuffixCodeString = String.format("%04d", newPendudukSuffixCode);

        	String newPendudukNIK = "" + newPendudukKodeLokasi + newPendudukDOBCode + newPendudukSuffixCodeString;

        	Integer newPendudukId = citizenshipDAO.selectLargestPendudukId() +1;
        	
        	PendudukModel newPm = new PendudukModel(newPendudukId, newPendudukNIK, nama, tempat_lahir, tanggal_lahir, jenis_kelamin, is_wni, id_keluarga, agama, pekerjaan, status_perkawinan, status_dalam_keluarga, golongan_darah, is_wafat, null);
        	citizenshipDAO.insertPenduduk(newPm);
        	
        	model.addAttribute("nik", newPendudukNIK);
        	return "penduduk-add-success";
    	} else {
    		return "penduduk-add-failed";
    	}
    	
    	
    }
    
    @RequestMapping("/penduduk/ubah")
    public String updatePendudukRequest(Model model, @RequestParam(value="nikToUpdate") String nik) {
    	PendudukModel pm = citizenshipDAO.selectPenduduk(nik);
    	if (pm != null) {
            model.addAttribute ("penduduk", pm);
            return "penduduk-ubah";
        } else {
        	model.addAttribute ("nik", nik);
            return "penduduk-not-found";
        }
    	
    }
    
    @PostMapping("/penduduk/ubah")
    public String submitUpdatePenduduk(Model model,
    		@RequestParam(value = "nama", required = false) String nama,
    		@RequestParam(value = "idToUpdate", required = false) Integer idToUpdate,
    		@RequestParam(value = "oldNik", required = false) String oldNik,
            @RequestParam(value = "tempat_lahir", required = false) String tempat_lahir,
            @RequestParam(value = "tanggal_lahir", required = false) String tanggal_lahir,
            @RequestParam(value = "jenis_kelamin", required = false) Integer jenis_kelamin,
            @RequestParam(value = "is_wni", required = false) Integer is_wni,
            @RequestParam(value = "id_keluarga", required = false) Integer id_keluarga,
            @RequestParam(value = "agama", required = false) String agama,
            @RequestParam(value = "pekerjaan", required = false) String pekerjaan,
            @RequestParam(value = "status_perkawinan", required = false) String status_perkawinan,
            @RequestParam(value = "golongan_darah", required = false) String golongan_darah,
            @RequestParam(value = "status_dalam_keluarga", required = false) String status_dalam_keluarga,
            @RequestParam(value = "is_wafat", required = false) Integer is_wafat
    		) {
    	
    	if(nama.equals("") || tempat_lahir.equals("") || tanggal_lahir.equals("") || agama.equals("") || pekerjaan.equals("") || status_dalam_keluarga.equals("") || status_perkawinan.equals("") || golongan_darah.equals("")) {
    		return "penduduk-ubah-failed";
    	}
    	
    	KeluargaModel km = citizenshipDAO.selectKeluargaById(id_keluarga);
    	if (km != null) {
        	String newPendudukKodeLokasi = km.getKelurahan().getKode_kelurahan().substring(0, 6);
        	
        	String hh = tanggal_lahir.substring(8);
        	String bb = tanggal_lahir.substring(5, 7);
        	String tt = tanggal_lahir.substring(2, 4);
        	
        	if (jenis_kelamin == 1) {
        		hh = "" + (Integer.parseInt(hh) + 40);
        	}
        	
        	String newPendudukDOBCode = hh + bb + tt; //YYYY-MM-DD
        	Integer newPendudukSuffixCode = citizenshipDAO.selectPendudukByGenderIncrement(newPendudukKodeLokasi + newPendudukDOBCode + "%") + 1;
        	String newPendudukSuffixCodeString = String.format("%04d", newPendudukSuffixCode);

        	String newPendudukNIK = "" + newPendudukKodeLokasi + newPendudukDOBCode + newPendudukSuffixCodeString;
        	
        	PendudukModel newPm = new PendudukModel(idToUpdate, newPendudukNIK, nama, tempat_lahir, tanggal_lahir, jenis_kelamin, is_wni, id_keluarga, agama, pekerjaan, status_perkawinan, status_dalam_keluarga, golongan_darah, is_wafat, null);
        	citizenshipDAO.updatePenduduk(newPm);
        	
        	model.addAttribute("nik", oldNik);
        	model.addAttribute("nikNew", newPendudukNIK);
        	return "penduduk-ubah-success";
    	} else {
    		return "penduduk-ubah-failed";
    	}
    	
    	
    }
   
    @PostMapping("/penduduk/mati")
    public String killPenduduk(Model model, @RequestParam(value = "nik", required = true) String nik) {
    	PendudukModel pm = citizenshipDAO.selectPenduduk(nik);
    	System.out.println("id keluarganya adalah " + pm.getKeluarga().getId());
    	pm.setIs_wafat(1);
    	citizenshipDAO.updatePenduduk(pm);
    	model.addAttribute("nik", pm.getNik());
    	return "mati-success";
    }
    
    @RequestMapping("/penduduk/cari")
    public String lookupPenduduk(Model model, 
    		@RequestParam(value = "kelurahan", required = false) Integer kelurahan,
            @RequestParam(value = "kecamatan", required = false) Integer kecamatan,
            @RequestParam(value = "kota", required = false) Integer kota) {

    	if (kelurahan != null) {
    		ArrayList<PendudukModel> listOfPenduduk = citizenshipDAO.searchPendudukByLocation(kelurahan);
    		model.addAttribute("listOfPenduduk", listOfPenduduk);
    		return "cari-penduduk-results";
    	} else if (kecamatan != null) {
    		ArrayList<KelurahanModel> listOfKel = citizenshipDAO.selectAllKelurahanByIdKecamatan(kecamatan);
    		KotaModel kom = citizenshipDAO.selectKotaById(kota);
    		KecamatanModel kcm = citizenshipDAO.selectKecamatanById(kecamatan);
    		model.addAttribute("kota", kom);
    		model.addAttribute("kecamatan", kcm);
    		model.addAttribute("listOfKel", listOfKel);
    		return "cari-penduduk3";
    	} else if (kota != null) {
    		ArrayList<KecamatanModel> listOfKec = citizenshipDAO.selectAllKecamatanByIdKota(kota);
    		KotaModel kom = citizenshipDAO.selectKotaById(kota);
    		model.addAttribute("kota", kom);
    		model.addAttribute("listOfKec", listOfKec);
    		return "cari-penduduk2";
    	} else {
    		ArrayList<KotaModel> listOfKota = citizenshipDAO.selectAllKota();
        	model.addAttribute("listOfKota", listOfKota);
        	return "cari-penduduk1";
    	}
    }
}
