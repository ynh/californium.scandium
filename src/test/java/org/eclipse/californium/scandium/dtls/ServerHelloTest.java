/*******************************************************************************
 * Copyright (c) 2015 Bosch Software Innovations GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and Eclipse Distribution License v1.0 which accompany this distribution.
 * 
 * The Eclipse Public License is available at
 *    http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at
 *    http://www.eclipse.org/org/documents/edl-v10.html.
 * 
 * Contributors:
 *    Kai Hudalla (Bosch Software Innovations GmbH) - initial creation
 ******************************************************************************/
package org.eclipse.californium.scandium.dtls;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.Arrays;

import org.eclipse.californium.scandium.dtls.CertificateTypeExtension.CertificateType;
import org.eclipse.californium.scandium.dtls.cipher.CipherSuite;
import org.junit.Before;
import org.junit.Test;

public class ServerHelloTest {

	ServerHello serverHello;
	
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testGetClientCertificateType() {
		givenAServerHelloWith(null, new CertificateType[]{CertificateType.RAW_PUBLIC_KEY, CertificateType.X_509});
		assertThat(serverHello.getClientCertificateType(), is(CertificateType.RAW_PUBLIC_KEY));
	}

	@Test
	public void testGetServerCertificateType() {
		givenAServerHelloWith(new CertificateType[]{CertificateType.RAW_PUBLIC_KEY, CertificateType.X_509}, null);
		assertThat(serverHello.getServerCertificateType(), is(CertificateType.RAW_PUBLIC_KEY));
	}

	private void givenAServerHelloWith(CertificateType[] serverTypes, CertificateType[] clientTypes) {
		HelloExtensions ext = new HelloExtensions();
		if (serverTypes != null) {
			ext.addExtension(new ServerCertificateTypeExtension(false, Arrays.asList(serverTypes)));
		}
		if (clientTypes != null) {
			ext.addExtension(new ClientCertificateTypeExtension(false, Arrays.asList(clientTypes)));
		}
		serverHello = new ServerHello(new ProtocolVersion(), new Random(), new SessionId(),
				CipherSuite.TLS_PSK_WITH_AES_128_CCM_8, CompressionMethod.NULL, ext);
	}
}