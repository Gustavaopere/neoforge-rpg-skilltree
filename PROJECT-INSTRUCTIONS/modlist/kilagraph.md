# KilaGraph

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db81edb55ad7e9e333eed6
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — top-level `21.1.0.14`, LDLib2 `2.2.39.a`, Photon `2.2.6.a` e JarJar homônimo confirmados fisicamente
- **Data da exportação:** 2026-09-11

## Propriedades do banco

- **Mod:** KilaGraph
- **Arquivo JAR:** `kilagraph-neoforge-1.21.1-21.1.0.14.jar`
- **Versão 1.21.1:** 21.1.0.14
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Biblioteca, Visual, Tecnologia
- **Função:** Toolkit de node graphs e shader graphs construído sobre o ecossistema LDLib2, oferecendo grafos programáveis, RenderType/Shader Function graphs e editor visual para mods consumidores.
- **Dependências:** Source exato 21.1.0.14 requer NeoForge >=21.1.217 e LDLib2 >=2.2.33. Pack físico: NeoForge 21.1.248 e LowDragLib2 2.2.39.a, satisfazendo os ranges. Photon 2.2.6.a também embute KilaGraph 21.1.0.14 como JarJar.
- **Sobreposição:** Não é shaderpack nem renderer standalone. É framework/editor consumido por outros mods. A cópia embutida em Photon é nested dependency do host e não mod top-level adicional; a igualdade de versão reduz, mas não elimina, necessidade de validar resolução do loader.
- **Compatibilidade/Riscos:** Build 21.1.0.14 é source-pinned e Beta. Riscos: LDLib2 ABI drift, graph serialization/schema drift, shader compilation/render-state errors, duplicate nested/top-level resolution e consumers usando nodes/graphs incompatíveis. Top-level e JarJar de Photon estão atualmente na mesma versão.
- **Observações:** Source pin exato fechado: 21.1.0.14. Pack satisfaz NeoForge/LDLib2 ranges. Não confundir o top-level #357 com a cópia JarJar homônima em Photon: nested JAR não ganha posição própria. Changelog version-specific 0.14 não foi localizado nesta auditoria.
- **Procedência:** modlist.txt física atual + source oficial Low-Drag-MC/KilaGraph branch 1.21 com `mod_version=21.1.0.14`, NeoForge range >=21.1.217 e LDLib2 range >=2.2.33 + índice de release 1.21.1/NeoForge + modlist registrando o mesmo KilaGraph 21.1.0.14 como JarJar dentro de Photon 2.2.6.a.
- **Fonte:** https://github.com/Low-Drag-MC/KilaGraph/tree/1.21
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 10/09/2026 — KilaGraph 21.1.0.14 source-pinned; NeoForge/LDLib2 ranges exatos, graph/shader architecture, Photon JarJar same-version boundary, Beta lifecycle, riscos e testes catalogados; changelog 0.14 não localizado.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-08-30

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO.** Runtime físico top-level: `kilagraph-neoforge-1.21.1-21.1.0.14.jar`, mod id `kilagraph`, versão `21.1.0.14`. A mesma versão aparece também como `META-INF/jarjar/kilagraph...21.1.0.14.jar` dentro de Photon 2.2.6.a; esse nested JAR é dependência do host e **não recebe posição top-level própria**.

## 1. Papel e authority
KilaGraph é infraestrutura de grafos programáveis e shader graphs para mods. Ele fornece modelos/editor/runtime de graph; o consumer continua authority do significado gameplay ou visual dos nodes que registra.

## 2. Blueprint Graphs
A documentação pública descreve Blueprint Graphs como grafos programáveis de lógica/data flow. Graph execution precisa ser determinístico conforme o contrato do consumer e não deve ser tratado como gameplay próprio de KilaGraph quando o efeito final pertence a outro mod.

## 3. RenderType Graphs
KilaGraph permite construir grafos ligados a pipelines `RenderType`/shader. Compilação de shader, resources e render state são client-side; falha gráfica não autoriza mutation de gameplay ou server state.

## 4. Shader Function Graphs
Subgrafos/funções reutilizáveis permitem compartilhar lógica de shader. Mudança de assinatura, port type ou resource ID pode quebrar graphs persistidos/empacotados por consumers mesmo sem alterar o nome visível do efeito.

## 5. LDLib2 e version gate
O source exato `Low-Drag-MC/KilaGraph:1.21` declara `mod_version=21.1.0.14`, NeoForge mínimo **21.1.217** e `ldlib2_version_range=[2.2.33,)`. O pack usa **NeoForge 21.1.248** e **LowDragLib2 2.2.39.a**, satisfazendo ambos os ranges publicados.

## 6. Photon — nested same-version
O top-level Photon 2.2.6.a contém um JarJar de **KilaGraph 21.1.0.14**, exatamente igual ao top-level. O loader deve resolver a identidade/mod version sem criar dois providers independentes. Não contar nested copy como mod separado e não criar ficha adicional.

## 7. Source pin 21.1.0.14
A modlist física continua authority de presença/JAR, e o source oficial branch `1.21` declara exatamente **`mod_version=21.1.0.14`**. Portanto esta ficha é **source-pinned**. Um changelog específico da 0.14 não foi localizado; não atribuir mudanças version-specific sem evidência.

## 8. Histórico anterior sem retroprojeção
A 21.1.0.12 documentou correções em whole-number/list ports e preparação para execução multithread; 0.13 refatorou vector nodes, adicionou tangent-space support e completou node wiki entries. São contexto acumulado da linha, não changelog atribuído à 0.14.

## 9. Serialization e resources
Graphs podem depender de node IDs, port types, settings e resources. Atualizar KilaGraph/LDLib2 pode invalidar serialized graphs de consumers. Migração precisa preservar IDs e falhar diagnosticavelmente quando node/type deixa de existir.

## 10. Client / server
Editor e shader/render graphs são majoritariamente client-facing, mas Blueprint Graphs podem participar de lógica de consumers. Sem metadata/source exato da 0.14, não se classifica toda a library como client-only. Qualquer path de gameplay deve permanecer server-authoritative no consumer.

## 11. Lifecycle
Validar registry/bootstrap, carregamento de graph resources, editor open/save, resource reload, shader compilation, world join e consumer load. Nested/top-level resolution deve permanecer estável em cold boot e dedicated server.

## 12. Riscos técnicos
- LDLib2 ABI drift;
- graph/node/port schema incompatível;
- serialized graph quebrar após update;
- shader compile/render-state failure;
- execução duplicada por registro repetido;
- nested/top-level version divergence futura;
- consumer assumir node removido;
- atribuir mudança 0.12/0.13 à 0.14 sem evidência.

## 13. Matriz de testes obrigatória
- [ ] Cliente e dedicated server iniciam com top-level KilaGraph 21.1.0.14.
- [ ] Loader não cria dois mods por causa do JarJar de Photon.
- [ ] LDLib2 2.2.39.a carrega sem linkage error.
- [ ] Photon abre/usa seus graph resources sem duplicate registration.
- [ ] Blueprint graph de consumer executa exatamente uma vez.
- [ ] Shader/RenderType graph compila sem missing node/resource.
- [ ] Resource reload não duplica graphs.
- [ ] Serialized graph sobrevive restart quando aplicável.
- [ ] Update futuro compara top-level e nested versions antes de adoção.

## 14. Evidências e limites
- **Modlist física:** top-level 21.1.0.14, LDLib2 2.2.39.a e nested KilaGraph 21.1.0.14 dentro de Photon.
- **Source oficial:** branch `1.21`, `mod_version=21.1.0.14`, NeoForge >=21.1.217 e LDLib2 >=2.2.33.
- **Documentação pública:** Blueprint/RenderType/Shader Function graphs e LDLib2 editor/toolkit.
- **Limite:** changelog específico da 21.1.0.14 não foi localizado; mudanças version-specific permanecem fail-closed.
- **Runtime:** nenhum teste acima foi executado nesta catalogação.
