# JourneyMap Integration

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db81e18ef5e73f4733e12d
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — JAR e providers integrados confirmados fisicamente
- **Data da exportação:** 2026-09-11

## Propriedades do banco

- **Mod:** JourneyMap Integration
- **Arquivo JAR:** `jmi-neoforge-1.21.1-1.9.jar`
- **Versão 1.21.1:** 1.21.1-1.9
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Compat, QoL
- **Função:** Bridge client-side para JourneyMap que projeta dados de FTB Chunks e Waystones no mapa sem assumir ownership de claims, teams ou waystones.
- **Dependências:** JourneyMap obrigatório. Pack físico: JourneyMap 1.21.1-6.0.7. Integrações suportadas: FTB Chunks 2101.1.22 e Waystones 21.1.44, ambos presentes. JMI 1.9 exige JourneyMap 6.0.0.beta45+ para os labels de overlay do FTB Chunks.
- **Sobreposição:** Complementa JourneyMap, FTB Chunks e Waystones; não substitui nenhum deles. A informação de claim/waystone exibida no mapa deve refletir o provider original e nunca ser tratada como ledger independente.
- **Compatibilidade/Riscos:** Client-side integration. Riscos: overlays/labels stale após mudança de team color ou unclaim, marker de Waystone órfão após remoção, coordinate/dimension mismatch e API drift do JourneyMap. Não criar segunda autoridade de claims ou teleport.
- **Observações:** Release 1.9 melhora claiming mode do FTB Chunks, otimiza render de labels e corrige atualização de overlay color após mudança da cor do time. O projeto declara ambiente client-side. Waystones é integração suportada; presença não implica que todo marker/config esteja habilitado.
- **Procedência:** modlist.txt física atual + CurseForge/Modrinth oficiais JourneyMap Integration 1.21.1-1.9 NeoForge, file 6508946 + changelog 1.9 + versões físicas JourneyMap/FTB Chunks/Waystones.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/journeymap-integration/files/6508946
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 10/09/2026 — JourneyMap Integration 1.21.1-1.9 release-pinned; FTB Chunks/Waystones overlays, JourneyMap API boundary, client lifecycle, stale-marker risks e matriz de testes catalogados.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-08-26

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO.** Runtime físico: `jmi-neoforge-1.21.1-1.9.jar`, mod id `jmi`, versão `1.21.1-1.9`. Release NeoForge 1.21.1 oficial. É uma bridge client-side para JourneyMap; claims, teams e waystones continuam pertencendo aos seus providers.

## 1. Papel e authority
JourneyMap Integration conecta JourneyMap a dados de outros mods. No pack vigente, as integrações concretas relevantes são **FTB Chunks 2101.1.22** e **Waystones 21.1.44**. JMI controla somente a tradução/apresentação desses dados no mapa.

## 2. Dependência JourneyMap
JourneyMap é requisito obrigatório. O pack usa JourneyMap 6.0.7. A release 1.9 documenta JourneyMap 6.0.0.beta45 como mínimo para o novo render de labels de FTB Chunks, portanto a versão física atual está além desse gate.

## 3. FTB Chunks
JMI pode mostrar claims/territórios do FTB Chunks no mapa. O owner do claim, team membership, permissões e mutation de claim continuam no FTB Chunks/FTB Teams; JMI não deve manter uma segunda verdade persistente.

## 4. Claiming mode e overlays
A 1.9 melhora o modo de claiming e otimiza como labels de overlay são renderizados no cliente. O mesmo changelog corrige overlay color que não atualizava após mudança da cor do time. Esses três pontos são regression gates da build instalada.

## 5. Waystones
Waystones é integração suportada. Marcadores podem representar waystones no JourneyMap, mas teleport, ativação, ownership e permissões continuam no mod Waystones. Um marker visível não prova que o teleport esteja permitido ou que a waystone ainda exista.

## 6. Lifecycle de marcadores
Validar criação/remoção de claim, mudança de team color, quebra/remoção de waystone, troca de dimensão, reconnect e reload de mapa. Dados stale devem desaparecer quando o provider deixa de expô-los.

## 7. Client / server
O projeto classifica a build como client-side. O cliente renderiza overlays/markers a partir de dados disponíveis/sincronizados pelos providers. Nenhuma alteração persistente em claims ou waystones deve ser liquidada por um evento puramente visual de JMI.

## 8. Coordenadas e dimensões
Waypoints/overlays precisam manter dimension e coordenadas corretas. Mods de portal, sublevel ou physics podem produzir contextos visuais especiais; JMI não deve converter coordenadas locais em world coordinates sem suporte explícito.

## 9. Relação com JourneyMap API
A integração depende da API de JourneyMap para overlays/markers. Atualizar JourneyMap isoladamente é version gate: mudança de API pode quebrar render, labels ou lifecycle mesmo sem alterar FTB Chunks/Waystones.

## 10. Riscos técnicos
- overlay de claim stale após unclaim;
- cor de team não atualizar;
- marker de waystone órfão;
- dimensão/coordenada errada;
- duplicate overlay por registro repetido;
- API drift do JourneyMap;
- confundir marker com autoridade de teleport/claim;
- cache client-side sobreviver a reconnect indevidamente.

## 11. Matriz de testes obrigatória
- [ ] Cliente inicia com JMI 1.9 + JourneyMap 6.0.7.
- [ ] Claims FTB Chunks aparecem na área correta.
- [ ] Claim/unclaim atualiza overlay sem stale region.
- [ ] Alterar cor do team atualiza overlay corretamente.
- [ ] Labels não causam regressão severa de FPS no mapa carregado.
- [ ] Waystone criada/removida atualiza marker sem órfão.
- [ ] Troca de dimensão mantém marker no dimension correto.
- [ ] Reconnect limpa cache incompatível.
- [ ] JMI não altera ownership/permissões dos providers.
- [ ] Update futuro de JourneyMap é bloqueado até smoke test da bridge.

## 12. Evidências e limites
- **Modlist física:** JAR/mod id/version e versions atuais de JourneyMap/FTB Chunks/Waystones.
- **CurseForge/Modrinth oficiais:** release 1.9, ambiente client-side, support matrix e changelog.
- **Limite:** configurações locais de JMI/JourneyMap não foram fornecidas; não se afirma quais overlays estão habilitados na sessão do usuário.
- **Runtime:** nenhum teste acima foi executado nesta catalogação.
