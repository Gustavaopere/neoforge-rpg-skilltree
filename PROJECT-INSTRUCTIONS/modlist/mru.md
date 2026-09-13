# MRU

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db81c5853cca8547d9d579
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — `mru-1.0.33+1.21.1-neoforge.jar`, mod id `mru`, runtime `1.0.33+1.21.1` confirmado fisicamente
- **Data da exportação:** 2026-09-11

## Divergência documental detectada na exportação

- A página Notion declara procedência por uma `modlist.txt física canônica atual de 10/09/2026`. A authority física mais recente realmente acessível nesta execução permanece o snapshot de **08/09/2026 com 595 top-levels**. Nesse snapshot, MRU 1.0.33+1.21.1 está confirmado.

## Propriedades do banco

- **Mod:** MRU
- **Arquivo JAR:** `mru-1.0.33+1.21.1-neoforge.jar`
- **Versão 1.21.1:** 1.0.33+1.21.1
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Biblioteca
- **Função:** Biblioteca reutilizável do ecossistema IMB11/Cassian para packed resources, helpers de configuração e abstrações comuns/multiversionadas de registro, inventário, bundles, backpacks e accessories usadas por consumers.
- **Dependências:** NeoForge 1.21.1. O projeto não publica hard dependency própria relevante; necessidade operacional depende de consumers instalados ainda não mapeados causalmente.
- **Sobreposição:** Não substituível automaticamente por outra library genérica. APIs equivalentes em conceito não são contratos drop-in.
- **Compatibilidade/Riscos:** Library multiversionada. Riscos: API/ABI drift, helper de versão incorreta, packed-resource/config drift e abstrações de inventory/backpack/accessory. Nenhum consumer físico inequívoco foi comprovado nesta passagem.
- **Observações:** Runtime físico 1.0.33+1.21.1. Linha moderna 1.0.30+ ampliou fortemente o escopo da library. Presença isolada não prova necessidade; não remover até mapear manifests dos consumers.
- **Procedência:** modlist.txt física canônica atual de 10/09/2026 + publicação/documentação oficial MRU + source upstream IMB11-Mods/MRU.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/mru
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 10/09/2026 — MRU 1.0.33+1.21.1 reconstruído: Packed Resources/YACL, arquitetura 1.0.30+, helpers multiversionadas, client/server, consumer mapping, riscos e testes. Nenhum consumer inequívoco foi provado; decisão permanece Sem decisão.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-08-26

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO.** Runtime físico: `mru-1.0.33+1.21.1-neoforge.jar`, mod id `mru`, versão `1.0.33+1.21.1`, em NeoForge 1.21.1. MRU é uma biblioteca do ecossistema IMB11/Cassian. A linha moderna ampliou o escopo além das antigas helpers de resources/YACL, mas nenhum consumer inequívoco foi comprovado na modlist física desta passagem; por isso a decisão permanece **Sem decisão** e a presença do JAR não é tratada como prova de necessidade.

## 1. Identidade e papel
- **Mod:** MRU — Mineblocks' Repeated Utilities.
- **JAR físico:** `mru-1.0.33+1.21.1-neoforge.jar`.
- **Mod id:** `mru`.
- **Runtime:** `1.0.33+1.21.1`.
- **Loader/jogo:** NeoForge 1.21.1.
- **Autor/ecossistema:** IMB11 / Cassian.
- **Ambiente publicado:** Client & Server.
- **Papel:** biblioteca reutilizável que centraliza infraestrutura comum para mods do mesmo ecossistema.

MRU não deve receber ownership do gameplay de seus consumidores. Quando um mod usa uma helper de MRU, a feature continua pertencendo ao consumer.

## 2. Escopo histórico documentado: Packed Resources
A documentação oficial de MRU descreve **Packed Resources** como uma forma de um consumer distribuir um resource pack padrão junto do mod e, quando suportado, permitir que esse conteúdo seja externalizado/editado.

Consequências operacionais:
- assets padrão podem vir empacotados pelo consumer;
- resource reload pode ser relevante para consumers que usam essa camada;
- arquivo externo gerado/editável não deve ser confundido com asset original do JAR;
- qualquer alteração manual precisa ser testada após update do consumer/MRU para evitar schema/resource drift.

## 3. YACL helpers
A documentação também registra helpers para construir telas/configurações usando YACL em consumers que optam por esse caminho.

Isso é infraestrutura de UI/config, não prova de que MRU imponha uma tela única a todos os seus consumers. A presença da API deve ser distinguida do uso efetivo por um mod específico.

## 4. Expansão da linha 1.0.30+
A documentação/publicação moderna informa que a biblioteca foi expandida para reduzir código repetido em mods como **Immersive Overlays** e **Immersive Minimaps** e passou a concentrar abstrações multiversionadas.

O source artifact da linha 1.0.30 documenta, entre outras áreas:
- hooks comuns de registro;
- consulta de inventário do jogador;
- helpers para bundles;
- backpacks;
- accessory APIs;
- helpers versionados para múltiplas linhas de Minecraft.

Esses itens descrevem a arquitetura moderna da biblioteca. Não significam que todos estejam executando neste pack sem um consumer identificado.

## 5. Release 1.0.33
A versão instalada `1.0.33+1.21.1-neoforge` foi publicada em 10/08/2026. O changelog da release registra **suporte à linha 26.3 e utilities versionadas**.

Para este pack, a parte relevante é que a mesma codebase mantém abstrações por versão; não se deve copiar implementação/config de outra linha de Minecraft para 1.21.1 sem validar a variante correspondente.

## 6. Client/server e ownership
O projeto é publicado como Client & Server porque diferentes helpers podem ser consumidas em lados diferentes. A library não é authority para:
- inventário persistente do player;
- backpacks/accessories fornecidos por outros mods;
- HUD/minimap/overlay de um consumer;
- regras de gameplay de um consumer.

Qualquer mutação real deve continuar validada pelo sistema que possui aquele estado.

## 7. Consumidores na modlist atual
Nesta passagem não foi encontrado um consumer inequívoco que permita afirmar causalmente que MRU é necessária ao boot do pack. Buscas por nomes esperados do ecossistema de minimap/overlay não estabeleceram um vínculo suficiente.

Portanto:
- **não remover** só porque o consumer não foi encontrado por nome;
- **não marcar Dependência** sem manifesto/source de um mod instalado declarando MRU;
- manter `Sem decisão` até o mapeamento causal dos consumidores ser concluído.

## 8. Configuração, resources e dados
A superfície mais concreta de configuração documentada é a infraestrutura para telas/configs e packed resources usada por consumers.

Não foi estabelecido neste lote um formato de SavedData, capability ou protocolo de rede próprio do MRU 1.0.33. Esses elementos não são inventados.

## 9. Compatibilidade
Riscos relevantes para uma library multiversionada:
1. **API/ABI drift:** consumer compilado contra outra versão pode falhar em classloading ou comportamento.
2. **Versioned helper mismatch:** chamar caminho destinado a outra versão do Minecraft pode quebrar abstrações.
3. **Packed resource drift:** consumer atualizado pode esperar assets/configs diferentes dos externalizados anteriormente.
4. **Inventory/accessory abstraction:** APIs de inventário/backpack/accessory precisam respeitar ownership e capabilities reais dos providers.
5. **UI/config dependency:** YACL ou outra UI só deve ser tratada como requisito quando o consumer/manifest comprovar.
6. **Attribution error:** stacktrace em MRU pode representar falha de integração do consumer e não bug intrínseco da biblioteca.

## 10. Matriz de testes
- [ ] Cliente e dedicated server iniciam com MRU 1.0.33 na composição atual.
- [ ] Identificar ao menos um consumer real por manifest/source antes de qualquer decisão de remoção.
- [ ] Consumer identificado abre suas telas/configurações sem missing class/API.
- [ ] Resource reload não quebra packed resources de consumers que usem esse recurso.
- [ ] Externalização/edição de resources, quando usada, sobrevive a restart de forma coerente.
- [ ] Inventário/bundle/backpack/accessory helper, quando realmente consumida, não duplica nem perde estado.
- [ ] Atualização futura de MRU é regressada com todos os consumers identificados.

Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 11. Evidências e limites
- Modlist física atual: `mru-1.0.33+1.21.1-neoforge.jar`, mod id `mru`, runtime `1.0.33+1.21.1`.
- Publicação oficial: versão NeoForge 1.21.1 1.0.33 de 10/08/2026.
- Documentação oficial: Packed Resources e YACL helpers.
- Linha moderna/source artifact: expansão para abstrações de registro, inventário, bundles, backpacks, accessories e helpers multiversionados.
- Source upstream: `IMB11-Mods/MRU`.
- **Limite:** nenhum consumer instalado foi provado causalmente neste lote; a ficha permanece deliberadamente fail-closed em `Sem decisão`.
