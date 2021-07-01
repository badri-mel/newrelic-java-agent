/*
 *
 *  * Copyright 2020 New Relic Corporation. All rights reserved.
 *  * SPDX-License-Identifier: Apache-2.0
 *
 */

package com.nr.agent.instrumentation.sttp

import com.nr.agent.instrumentation.sttp.Sttp2Utils.{finishSegment, startSegment}
import sttp.client.{FollowRedirectsBackend, Identity, NothingT, Request, Response, SttpBackend}

class DelegateIdentity(delegate: FollowRedirectsBackend[Identity, Any, Any]) extends SttpBackend[Identity, Nothing, NothingT] {
  override def send[T](request: Request[T, Nothing]): Identity[Response[T]] = {
    val segment = startSegment(request)

    val response = delegate.send(request)

    finishSegment(request, segment, response)

    response
  }

  override def openWebsocket[T, WS_RESULT](request: Request[T, Nothing], handler: NothingT[WS_RESULT]) = delegate.openWebsocket(request, handler)

  override def close() = delegate.close()

  override def responseMonad = delegate.responseMonad
}
