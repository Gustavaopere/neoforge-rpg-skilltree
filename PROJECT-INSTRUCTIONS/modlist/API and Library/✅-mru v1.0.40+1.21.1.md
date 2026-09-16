# MRU

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Arquivo JAR:** `mru-1.0.40+1.21.1-neoforge.jar`
- **Versão 1.21.1:** 1.0.40+1.21.1
- **Estado no pack:** Integrado ao Github
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Biblioteca
- **Função:** Biblioteca reutilizável do ecossistema IMB11/Cassian para packed resources, helpers de configuração e abstrações comuns/multiversionadas de registro, inventário, bundles, backpacks e accessories usadas por consumers.
- **Dependências:** NeoForge 1.21.1. A necessidade operacional depende de consumers instalados; nenhum consumer top-level inequívoco foi comprovado causalmente nesta reauditoria.
- **Sobreposição:** Não substituível automaticamente por outra library genérica. APIs equivalentes em conceito não são contratos drop-in.
- **Compatibilidade/Riscos:** Library multiversionada. Riscos: API/ABI drift, helper de versão incorreta, packed-resource/config drift e abstrações de inventory/backpack/accessory. O salto 1.0.33→1.0.40 exige regression dos consumers quando eles forem identificados.
- **Observações:** Runtime físico `1.0.40+1.21.1`. A publicação oficial confirma MRU 1.0.40 para NeoForge 1.21.1 em 11/09/2026. O material upstream recuperado não expôs changelog específico suficiente para atribuir deltas internos à 1.0.40; nenhuma mudança funcional foi inventada.
- **Procedência:** modlist física de 16/09/2026 + CurseForge oficial MRU 1.0.40 para NeoForge 1.21.1 + documentação/source previamente auditados para Packed Resources, YACL helpers e abstrações multiversionadas. Nenhum teste runtime/linkage foi executado.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/mru
- **Atualização/Status:** REAUDITADO EM 16/09/2026 — runtime físico atualizado de 1.0.33 para 1.0.40+1.21.1. Release 1.0.40 confirmada; delta interno não documentado nas fontes recuperadas e portanto não inferido.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-08-26

> 🔎 **ESCOPO CANÔNICO.** Runtime físico: `mru-1.0.40+1.21.1-neoforge.jar`, mod id `mru`, versão `1.0.40+1.21.1`, NeoForge 1.21.1. MRU é uma library do ecossistema IMB11/Cassian. Presença do JAR não prova necessidade; a decisão permanece **Sem decisão** até haver consumer instalado comprovado por manifest/source.

## 1. Identidade e papel
- **Mod:** MRU — Mineblocks' Repeated Utilities.
- **JAR físico:** `mru-1.0.40+1.21.1-neoforge.jar`.
- **Mod id:** `mru`.
- **Runtime:** `1.0.40+1.21.1`.
- **Loader/jogo:** NeoForge 1.21.1.
- **Autor/ecossistema:** IMB11 / Cassian.
- **Ambiente publicado:** Client & Server.
- **Papel:** centralizar infraestrutura comum reutilizável para consumers do ecossistema.

MRU não recebe ownership do gameplay de seus consumers. O sistema consumidor continua authority da feature e de seu state.

## 2. Packed Resources
A documentação oficial descreve **Packed Resources** como mecanismo para um consumer distribuir resource pack padrão junto do mod e, quando suportado, externalizar/editar esse conteúdo.

Consequências preservadas do dossiê do Notion:
- assets padrão podem vir empacotados pelo consumer;
- resource reload pode ser relevante;
- arquivo externalizado não é o asset original do JAR;
- edição manual precisa de regression após update para evitar schema/resource drift.

## 3. YACL helpers
MRU documenta helpers para construção de telas/configurações com YACL quando um consumer opta por esse caminho. Isso é infraestrutura; não prova que todos os consumers usem YACL nem que MRU seja authority do significado das configs.

## 4. Expansão da linha 1.0.30+
A linha moderna foi expandida para reduzir código repetido em projetos como Immersive Overlays/Immersive Minimaps e concentra abstrações multiversionadas. O material previamente auditado documenta:
- hooks comuns de registro;
- consulta de inventário do jogador;
- helpers para bundles;
- backpacks;
- accessory APIs;
- helpers específicos por linha de Minecraft.

A existência dessas APIs não prova uso efetivo no pack sem consumer identificado.

## 5. Histórico 1.0.33 e runtime 1.0.40
A antiga versão catalogada `1.0.33+1.21.1-neoforge` foi publicada em 10/08/2026; seu changelog registrava suporte à linha 26.3 e utilities versionadas. Isso continua contexto arquitetural útil, mas não é mais o runtime.

A versão instalada atual é **1.0.40+1.21.1**, publicada oficialmente para NeoForge 1.21.1 em 11/09/2026. As fontes recuperadas nesta reauditoria confirmam arquivo/versão/plataforma, mas não forneceram changelog granular de 1.0.34→1.0.40. Por regra fail-closed, não são atribuídas features ou fixes específicos sem evidência.

## 6. Client/server e ownership
MRU é publicada como Client & Server porque helpers podem ser consumidos nos dois lados. A library não é authority para:
- inventário persistente do player;
- backpacks/accessories de terceiros;
- HUD/minimap/overlay do consumer;
- regras de gameplay do consumer.

Mutação real continua sob validação do provider/consumer que possui o state.

## 7. Consumidores na modlist atual
O dossiê original não comprovou consumer inequívoco, e esta reauditoria não promoveu nenhum vínculo novo sem manifesto/source. Portanto:
- não remover MRU por ausência de consumer evidente por nome;
- não marcar `Dependência` sem prova causal;
- manter `Sem decisão` até o mapeamento ser fechado.

## 8. Configuração, resources e dados
A superfície concreta documentada continua sendo infraestrutura de telas/configs e packed resources. Não foi estabelecido formato específico de SavedData, capability ou protocolo de rede próprio do runtime 1.0.40; esses elementos não são inferidos.

## 9. Compatibilidade
1. **API/ABI drift:** consumer compilado contra outra versão pode falhar em classloading ou comportamento.
2. **Versioned helper mismatch:** caminho de outra versão do Minecraft pode quebrar abstrações.
3. **Packed resource drift:** consumer atualizado pode esperar assets/configs diferentes.
4. **Inventory/accessory abstraction:** helpers devem respeitar ownership/capabilities reais.
5. **UI/config dependency:** YACL só é requisito quando consumer/manifest comprovar.
6. **Attribution error:** stacktrace em MRU pode ser efeito de consumer incompatível.
7. **Delta 1.0.40 não pinado:** sem changelog granular, regression deve ser orientada pelos consumers reais.

## 10. Matriz de testes
- [ ] Cliente e dedicated server iniciam com MRU 1.0.40 na composição atual.
- [ ] Identificar consumer real por manifest/source antes de decisão de remoção.
- [ ] Consumer identificado carrega sem missing class/API.
- [ ] Resource reload não quebra packed resources utilizados.
- [ ] Externalização/edição, quando usada, persiste coerentemente.
- [ ] Helpers de inventário/bundle/backpack/accessory, quando consumidos, não duplicam nem perdem state.
- [ ] Atualizações futuras são regressadas com todos os consumers identificados.

**Nenhum teste foi executado nesta reauditoria documental.**

## 11. Evidências e limites
- Modlist física de 16/09/2026: `mru-1.0.40+1.21.1-neoforge.jar`, mod id `mru`, runtime `1.0.40+1.21.1`.
- Publicação oficial: MRU 1.0.40 para NeoForge 1.21.1 em 11/09/2026.
- Documentação previamente auditada: Packed Resources, YACL helpers e abstrações modernas de registro/inventário/bundles/backpacks/accessories.
- Source upstream: `IMB11-Mods/MRU`.
- **Limite:** não foi obtido changelog granular da 1.0.40 nem comprovado consumer instalado; ambos permanecem explicitamente não inferidos.

## 12. Reauditoria física — 16/09/2026
O runtime físico mudou de `1.0.33+1.21.1` para `1.0.40+1.21.1`. A ficha preserva integralmente o escopo técnico migrado do Notion e atualiza apenas fatos comprovados para a build nova. A decisão continua **Sem decisão** e nenhum teste de boot/linkage/resource reload foi executado.