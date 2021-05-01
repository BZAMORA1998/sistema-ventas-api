package com.sistema.ventas.bo;

import java.util.List;

import com.sistema.ventas.dto.ConsultarRolesDTO;
import com.sistema.ventas.exceptions.BOException;

public interface IRolesBO {

	List<ConsultarRolesDTO> consultarRoles(String username)throws BOException;

}