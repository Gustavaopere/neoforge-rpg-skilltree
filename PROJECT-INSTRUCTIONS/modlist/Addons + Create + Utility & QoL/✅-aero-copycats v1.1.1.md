# aero_copycats

> **Autoridade física atual — 22/09/2026.** `modlist(1).txt` contém **587 entradas top-level incluindo o modloader**; este item ocupa a ordem física **#13**: `aerocopycats-1.1.1.jar`, mod id `aerocopycats`, metadata/runtime `1.1.0`, SHA-1 `3a93862421d0a8cdae1e3a3d7f0d4bd01e049007`. A versão de distribuição/filename é `1.1.1`; a divergência com o metadata interno `1.1.0` é preservada, não normalizada.

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist(1).txt` de 16/09/2026 — autoridade física atual
- **Data da exportação:** 2026-09-09

## Propriedades do banco

- **Mod:** aero_copycats
- **Arquivo JAR:** `aerocopycats-1.1.1.jar`
- **Versão 1.21.1:** `1.1.0`
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 14/09/2026 — ownership de massa, boundary com Copycat Wing, lifecycle, multiplayer e fingerprint físico documentados; divergência filename 1.1.1 ↔ runtime 1.1.0 preservada.
- **Categoria:** Compat
- **Compatibilidade/Riscos:** Pode alterar massa total, centro de massa e estabilidade de ships. Updates de Copycats+ podem introduzir formas ainda não cobertas; validar catálogo atual, montagem/desmontagem e persistência.
- **Decisão:** Sem decisão
- **Dependências:** Required upstream: Create Aeronautics + Create: Copycats+. Pack atual: Aeronautics 1.3.2 (bundle) e Copycats+ 3.0.9+mc.1.21.1-neoforge presentes. Sable é a infraestrutura física subjacente.
- **Estado da pesquisa:** Verificado
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/copycats-aeronautics-weight
- **Função:** Define massa física (kpg) coerente para blocos do Create: Copycats+ em Create Aeronautics/Sable, incluindo escala por número de camadas em Copycat Layers.
- **Histórico da decisão:** vazio
- **Observações:** Não normalizar versão: publicação/filename `1.1.1`, runtime `1.1.0`. Nenhum mixin config foi exposto pelo inventário físico para este JAR; isso não prova ausência de integração em código.
- **Procedência:** modlist.txt física do projeto consultada em 14/09/2026 + CurseForge oficial Copycats+ Aeronautics Weight. Artefato: `aerocopycats-1.1.1.jar`, metadata/runtime `1.1.0`, SHA-1 `3a93862421d0a8cdae1e3a3d7f0d4bd01e049007`; divergência preservada por evidência.
- **Sobreposição:** Complementar a Create Aeronautics: Copycat Wing 1.0.3: Aero Copycats define massa; Copycat Wing define reconhecimento aerodinâmico/lift. Não são duplicatas.
- **Data da última decisão:** vazio
- **Estado no pack:** Integrado ao Github

## Escopo e papel
Addon de física para **Create: Copycats+** dentro de **Create Aeronautics/Sable**. Sua responsabilidade é atribuir massa (`kpg`) coerente às formas Copycat, evitando que geometrias diferentes sejam tratadas com peso genérico. Em `Copycat Layers`, a massa escala conforme a quantidade de camadas.

## Runtime e autoridade
- JAR físico: `aerocopycats-1.1.1.jar`.
- Mod ID: `aerocopycats`.
- Nome runtime: `aero_copycats`.
- Runtime metadata: **1.1.0**.
- Arquivo/release publicada: **1.1.1**.
A divergência filename/publicação ↔ metadata runtime é preservada; não deve ser normalizada sem evidência do JAR.

## Dependências
- **Required no CurseForge:** Create Aeronautics e Create: Copycats+.
- Pack atual: Copycats+ `3.0.9+mc.1.21.1-neoforge` e Aeronautics `1.3.2` estão presentes.
- Sable é o solver físico sobre o qual a massa produz efeito, mas a página de relações do projeto publica Aeronautics + Copycats+ como dependências requeridas.

## Integrações no pack
Existe também `Create Aeronautics: Copycat Wing 1.0.3`. Os dois addons são complementares: **Aero Copycats define massa**, enquanto **Copycat Wing registra Copycats como superfícies aerodinâmicas/lift**. Uma peça pode portanto participar simultaneamente de massa e aerodinâmica sem duplicação lógica.

## Compatibilidade, sobreposição e riscos
O ponto sensível é balanceamento físico: alterações nas massas podem mudar centro de massa, estabilidade, inércia e comportamento de veículos já construídos. Atualizações de Copycats+ que adicionem novas formas precisam ser testadas para cobertura. Não há evidência de um segundo top-level no pack exercendo a mesma função de massa para todo o catálogo Copycats+.

## Limites
Não cria blocos Copycat, não muda textura/camuflagem e não transforma uma forma em wing por si só. Também não é um controlador de voo.


## Ownership técnico e superfícies alteradas
- **Ownership primário:** massa física de blocos/formas do `Create: Copycats+` quando usados em ships do stack Aeronautics/Sable.
- **Mod ID físico:** `aerocopycats`; nome runtime exibido: `aero_copycats`.
- O inventário físico não expõe nome de mixin config para este JAR. Isso **não é evidência de ausência de integração em código**; apenas significa que nenhum config de mixin foi listado pelo extrator.
- A fronteira funcional permanece clara: este addon define **massa**; `Create Aeronautics: Copycat Wing` trata **reconhecimento aerodinâmico/lift**.
## Configuração e dados
Nenhuma superfície de configuração de usuário foi confirmada nas fontes auditadas. O dado operacional essencial é a atribuição de massa por forma e, em `Copycat Layers`, a escala conforme o número de camadas. Valores numéricos internos de massa por bloco não foram inventariados neste lote e não devem ser presumidos.
## Client/server, lifecycle e multiplayer
Massa, centro de massa e resposta física precisam produzir o mesmo resultado no servidor e nos clientes observadores. O lifecycle sensível é **colocar/alterar Copycats → montar ship → recalcular massa/inércia → mover → desmontar → salvar/recarregar**. Em multiplayer, divergência de mass properties pode aparecer como jitter, correção de posição ou comportamento diferente entre host e cliente, por isso o teste deve ser feito também em dedicated server.
## Fingerprint físico e divergência de versão
- JAR: `aerocopycats-1.1.1.jar`
- Metadata/runtime: `1.1.0`
- SHA-1: `3a93862421d0a8cdae1e3a3d7f0d4bd01e049007`
- **Não normalizar** `1.1.1` para `1.1.0` nem o inverso: filename/publicação e metadata runtime divergem no artefato físico e ambos são fatos relevantes.

## Testes recomendados
1. Comparar massa total do ship com/sem diferentes formas Copycat.
2. Testar Copycat Layers com 1, múltiplas e máxima quantidade de camadas.
3. Combinar com Copycat Wing e validar que massa e lift coexistem.
4. Montar/desmontar e salvar/recarregar ships sem perda de propriedades.
5. Testar formas novas adicionadas pelo Copycats+ atual para detectar lacunas de cobertura.

## Evidências
- [CurseForge oficial — Copycats+ aeronautics weight](https://www.curseforge.com/minecraft/mc-mods/copycats-aeronautics-weight)
- Modlist física atual e guia consolidado de Tecnologia.
