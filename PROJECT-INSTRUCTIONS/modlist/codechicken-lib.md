# CodeChicken Lib

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db81bdb040edf5b2605c49
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist.txt` — 595 mods top-level
- **Data da exportação:** 2026-09-09

## Propriedades do banco

- **Mod:** CodeChicken Lib
- **Arquivo JAR:** `CodeChickenLib-1.21.1-4.6.1.529.jar`
- **Versão 1.21.1:** `4.6.1.529`
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 08/09/2026 — math/render/network/config/ASM library surfaces, consumer contract, client/server lifecycle, packet/version drift e Quack jarjar catalogados.
- **Categoria:** Biblioteca
- **Compatibilidade/Riscos:** Riscos de ABI/version drift em rendering, packets/network codecs, config e transformations; client renderer no dedicated server; packet mismatch entre cliente/servidor; e Quack embedded coexistindo com outras cópias. Não é substituível por outras libraries genéricas.
- **Decisão:** Sem decisão
- **Dependências:** Biblioteca de infraestrutura, Client & Server. Necessidade determinada pelos consumers. O JAR físico embarca `Quack-0.4.10.115.jar` em `/META-INF/jarjar/`; essa library embedded não é top-level separada.
- **Estado da pesquisa:** Verificado
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/codechicken-lib-1-8
- **Função:** Biblioteca TheCBProject/ChickenBones com infraestrutura compartilhada para 3D math/transformations, model rendering, networking/packets, configs, colours, ASM e outras utilities usadas por mods consumidores.
- **Histórico da decisão:** Sem decisão formal. Em 08/09/2026, CodeChicken Lib 4.6.1.529 foi reconfirmado no JAR físico e reconstruído ao padrão técnico. Sua presença como dependency library não foi convertida em decisão curatorial.
- **Observações:** mod id `codechickenlib`; runtime 4.6.1.529. Projeto oficial declara libraries de 3D math/transformations, rendering, networking, configs, colours, ASM e outras utilities. Quack 0.4.10.115 é embedded, não top-level.
- **Procedência:** Modlist física mais recente de 07/09/2026 + CurseForge oficial CodeChicken Lib 4.6.1.529 + source oficial TheCBProject/CodeChickenLib + inspeção/registro físico do Quack jar-in-jar.
- **Sobreposição:** Library específica TheCBProject. Coexistência com outras math/render/network/config APIs não implica redundância binária; consumers compilam contra contracts próprios.
- **Data da última decisão:** vazio

## Dossiê operacional — padrão Alex's Mobs

> ✅ Versão física confirmada: `CodeChickenLib-1.21.1-4.6.1.529.jar`, mod id `codechickenlib`, runtime `4.6.1.529`, NeoForge 1.21.1. O JAR hospeda `Quack-0.4.10.115.jar` via **jar-in-jar**; Quack não é top-level deste lote.

## 1. Papel e authority
CodeChickenLib é uma library para mods do ecossistema ChickenBones/TheCBProject. O projeto oficial descreve infraestrutura para **3D math e transformations, model rendering, packets/networking, configs, colours, ASM e outras utilities**.

O consumer continua authority da máquina, item, energia, inventário ou gameplay que usa essas APIs.

## 2. Math e transformations
Rotação, matrizes/vetores e transformations são infraestrutura comum para render/model/geometry de consumers. Não tratar um resultado visual calculado no cliente como state de gameplay sem o consumer explicitamente usá-lo no common/server.

Version drift nessa superfície pode gerar modelos incorretos sem necessariamente causar crash imediato.

## 3. Rendering
Códigos de model rendering são client-facing. Dedicated server não pode depender de renderer para bootstrap de registry ou lógica comum.

Resource reload, model bake e render-state caches precisam invalidar corretamente; consumer deve ser identificado antes de atribuir regressão visual à library.

## 4. Networking e packets
CodeChickenLib fornece infraestrutura de packets/networking. O payload real e sua semântica pertencem ao consumer.

Regras de integração:
- cliente e servidor precisam concordar sobre protocol/codec do consumer;
- validar tamanho/tipo antes de aplicar state;
- evitar processar o mesmo action packet duas vezes;
- UI packet não deve autorizar ação que o servidor rejeita.

## 5. Config infrastructure
Utilities de config auxiliam consumers, mas defaults, ranges, persistência e authority pertencem a cada mod consumidor.

Não ler um config helper CCL como fonte universal de policy do pack.

## 6. Colours e presentation utilities
Helpers de cor podem ser usados em rendering/UI/data transforms. Valor visual não deve ser reinterpretado como atributo de gameplay sem contract explícito do consumer.

## 7. ASM/core hooks
O projeto oficial cita ASM entre suas utilities. Isso aumenta sensibilidade a versão do Minecraft/NeoForge e transformação de bytecode.

Falha nessa camada pode aparecer como class transformation/mixin-like startup issue; diagnóstico deve preservar stack trace e identificar a classe consumer/target.

## 8. Quack jar-in-jar
O artefato físico registra `Quack-0.4.10.115.jar` em `/META-INF/jarjar/`. Pela regra canônica do catálogo:
- não criar página top-level para essa cópia;
- não considerar a library “ausente” do pack;
- classloading/debug precisa considerar que a origem é embedded no host CCL.

Se outra cópia de Quack surgir top-level em modlist futura, avaliar resolução de versão em vez de removê-la por inferência.

## 9. Consumer-driven necessity
A página CurseForge classifica CCL como API/Library, Client & Server. A necessidade é determinada pelos mods que a requerem.

Não remover porque “não adiciona conteúdo”; esse é precisamente o comportamento esperado de uma dependency library.

## 10. Client/server
- networking/common utilities: ambos os lados conforme consumer;
- rendering/model: cliente;
- gameplay state: servidor/consumer;
- config authority: consumer-specific.

Separar side evita `NoClassDefFoundError` de classes gráficas no dedicated server.

## 11. Lifecycle
Validar construction/bootstrap, registry do consumer, network registration, client join, resource reload, config load/reload, disconnect e server restart.

Packet channels/handlers não devem ser registrados duas vezes depois de reload/reconnect.

## 12. Version drift
Sintomas possíveis:
- `NoSuchMethodError`/`NoClassDefFoundError`;
- packet decode mismatch;
- render/model crash;
- transformation/ASM failure;
- config callback incompatível;
- Quack dependency resolution conflict.

Atualizar a library isoladamente exige smoke-test dos consumers físicos.

## 13. Riscos
1. Remover CCL com consumer ativo.
2. ABI/API drift.
3. Packet desync cliente-servidor.
4. Client renderer class no dedicated server.
5. Model/transform cache stale após reload.
6. ASM hook incompatível com NeoForge/consumer.
7. Quack embedded tratado incorretamente como top-level.

## 14. Matriz de testes
1. Dedicated server boot.
2. Client join com versões idênticas do pack.
3. Smoke-test dos consumers que exigem CCL.
4. Interfaces/network actions desses consumers sem packet error.
5. Resource reload/model rendering.
6. Config save/reload dos consumers.
7. Disconnect/reconnect sem handler duplication.
8. Logs sem linkage/ASM failures.
9. Verificar origem de Quack no classpath durante troubleshooting.

## 15. Evidência
- modlist física: CodeChicken Lib 4.6.1.529;
- CurseForge oficial: API/library, Client & Server, latest 1.21.1 build 4.6.1.529;
- source oficial: math/transformations, rendering, packets, config, colours, ASM e outras utilities;
- registro físico: Quack 0.4.10.115 embedded em `/META-INF/jarjar/`.

> 🐔 Boundary canônico: CCL fornece **infraestrutura compartilhada**; state e comportamento final permanecem sob authority dos consumers.
